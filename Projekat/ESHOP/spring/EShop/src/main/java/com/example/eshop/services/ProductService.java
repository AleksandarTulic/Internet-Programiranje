package com.example.eshop.services;

import com.example.eshop.exceptions.NotFoundException;
import com.example.eshop.exceptions.ValidationFailedException;
import com.example.eshop.exceptions.YouAreNotTheOwnerException;
import com.example.eshop.models.Product;
import com.example.eshop.models.ProductFieldValues;
import com.example.eshop.models.ProductResponse;
import com.example.eshop.models.ProductSearchRequest;
import com.example.eshop.models.entities.*;
import com.example.eshop.repositories.*;
import com.example.eshop.services.utilities.FileService;
import com.example.eshop.validation.ValidationProduct;
import com.example.eshop.validation.ValidationProductFieldValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private static final String SAVE_PICTURES_FOLDER = "products";
    private static final String ANY_TYPE = "ANY";
    private final ProductRepository productRepository;
    private final ProductFieldValuesRepository productFieldValuesRepository;
    private final ProductPicturesRepository productPicturesRepository;
    private final CategoryFieldsRepository categoryFieldsRepository;
    private final FileService fileService;
    private final ConsumerRepository consumerRepository;
    private final ProductPictureService productPictureService;
    private final LogService logService;

    public ProductEntity save(Product request, Long id){
        List<ProductFieldValues> arrFields = request.getFieldValues();
        List<MultipartFile> arrImages = request.getPictures();
        List<ProductFieldValuesEntity> arrFieldValuesEntity = new ArrayList<>();

        ProductEntity productEntity = new ProductEntity(id, request.getTitle(), request.getDescription(), request.getCost(), request.getProductType(), request.getCategoryTitle(), request.getConsumerId());

        ProductEntity s = null;
        if (new ValidationProduct(productEntity).check()){
            s = productRepository.save(productEntity);

            if (arrFields != null) {
                for (ProductFieldValues i : arrFields)
                    arrFieldValuesEntity.add(new ProductFieldValuesEntity(request.getCategoryTitle(), i.getCategoryFieldName(), s.getId(), i.getFieldValue()));
            }

            for (ProductFieldValuesEntity i : arrFieldValuesEntity){
                CategoryFieldsEntity categoryFieldsEntity = categoryFieldsRepository.findById(new CategoryFieldsEntityPK(i.getCategoryFieldTitle(), i.getCategoryFieldName())).get();

                if (new ValidationProductFieldValue(i, categoryFieldsEntity).check())
                    productFieldValuesRepository.save(i);
            }

            ConsumerEntity c = consumerRepository.findById(s.getConsumerId()).get();

            if (id != null){
                productPicturesRepository.deleteByProductId(id);
                fileService.deleteAllContent(c.getUserEntity().getUsername() + File.separator + SAVE_PICTURES_FOLDER + File.separator + s.getId());
            }

            if (arrImages != null) {
                for (MultipartFile i : arrImages) {
                    ProductPicturesEntity productPicturesEntity = new ProductPicturesEntity();
                    productPicturesEntity.setName(i.getOriginalFilename());
                    productPicturesEntity.setProductId(s.getId());
                    productPicturesRepository.save(productPicturesEntity);

                    fileService.saveFile(c.getUserEntity().getUsername() + File.separator + SAVE_PICTURES_FOLDER + File.separator + s.getId(), i);
                }
            }
        }else{
            logService.save("Class: ProductService. Method: save. Reason: You need to input correct field values.", LogService.LEVEL_DANGER, null);
            throw new ValidationFailedException("You need to input correct field values.");
        }

        return s;
    }

    public ProductEntity retrieveById(Long id){
        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if (productEntity.isPresent()) {
            //security info should not be available to all
            productEntity.get().getConsumerEntity().getUserEntity().setPassword(null);
            productEntity.get().getConsumerEntity().setConfirmationTokenEntity(null);

            return productEntity.get();
        }else{
            logService.save("Class: ProductService. Method: retrieveById. Reason: Product with product_id=" + id + " does not exist.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Product with product_id=" + id + " does not exist.");
        }
    }

    public Boolean deleteById(Long productId, Long consumerId){
        Optional<ProductEntity> productEntity = productRepository.findById(productId);

        if (productEntity.isPresent()) {
            if (productEntity.get().getConsumerId() == consumerId) {
                productRepository.deleteById(productId);
            }else{
                logService.save("Class: ProductService. Method: deleteById. Product with product_id=" + productId + " can not be deleted by you because you are not the owner.", LogService.LEVEL_DANGER, null);
                throw new YouAreNotTheOwnerException("Product with product_id=" + productId + " can not be deleted by you because you are not the owner.");
            }
        }else{
            logService.save("Class: ProductService. Method: deleteById. Product with product_id=" + productId + " does not exist.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Product with product_id=" + productId + " does not exist.");
        }

        return !productRepository.existsById(productId);
    }

    public List<ProductResponse> retrieveBySpecificConditions(ProductSearchRequest productSearchRequest){
        List<ProductResponse> arrReturn = retrieveByCondition(productSearchRequest);
        return arrReturn.subList(Math.min(productSearchRequest.getLeft(), arrReturn.size()), Math.min(productSearchRequest.getRight(), arrReturn.size()));
    }

    public Long retrieveNumberOfProducts(ProductSearchRequest productSearchRequest){
        return retrieveByCondition(productSearchRequest).size() + 0L;
    }

    private List<ProductResponse> retrieveByCondition(ProductSearchRequest productSearchRequest){
        List<ProductEntity> arr = productRepository.findAll();
        List<ProductResponse> arrReturn = new ArrayList<>();

        productSearchRequest.setLowerCost(productSearchRequest.getLowerCost() == null ? 0 : productSearchRequest.getLowerCost());
        productSearchRequest.setUpperCost(productSearchRequest.getUpperCost() == null ? Long.MAX_VALUE : productSearchRequest.getUpperCost());

        for (ProductEntity i : arr){

            if (i.getCost() >= productSearchRequest.getLowerCost() && i.getCost() <= productSearchRequest.getUpperCost() &&
                    ("".equals(productSearchRequest.getProductTitle()) || i.getTitle().contains(productSearchRequest.getProductTitle())) &&
                    ("".equals(productSearchRequest.getCity()) || productSearchRequest.getCity().equals(i.getConsumerEntity().getCity())) &&
                    ("".equals(productSearchRequest.getCategoryTitle()) || productSearchRequest.getCategoryTitle().equals(i.getCategoryTitle()) || ANY_TYPE.equals(productSearchRequest.getCategoryTitle())) &&
                    ("".equals(productSearchRequest.getProductType()) || productSearchRequest.getProductType().equals(i.getProductType()) || ANY_TYPE.equals(productSearchRequest.getProductType())) &&
                    ("".equals(productSearchRequest.getDescription()) || i.getDescription().contains(productSearchRequest.getDescription()))){

                List<byte[]> arrPictures = productPictureService.retrieveByProductId(i.getId());
                arrReturn.add(new ProductResponse(i.getId(), arrPictures.size() == 0 ? null : arrPictures.get(0), i.getTitle(), i.getCategoryTitle(), i.getCost()));
            }
        }

        return arrReturn;
    }

    public Boolean exists(Long id){
        return productRepository.existsById(id);
    }


    //this products belong to the user with consumerId but they are not bought yet
    public List<ProductResponse> retrieveProductsNotBought(Long consumerId, Integer left, Integer right){
        List<ProductResponse> arr = new ArrayList<>();
        for (ProductEntity i : productRepository.findAllNotBought(consumerId)){
            List<byte[]> arrPictures = productPictureService.retrieveByProductId(i.getId());
            arr.add(new ProductResponse(i.getId(), arrPictures.size() == 0 ? null : arrPictures.get(0),
                    i.getTitle(), i.getCategoryTitle(), i.getCost()));
        }

        return arr.subList(Math.min(arr.size(), left), Math.min(arr.size(), right));
    }

    public Long retrieveNumberProductsNotBought(Long consumerId){
        return productRepository.countByNotBought(consumerId);
    }


    //this products someone bought from the user with consumerId
    public List<ProductResponse> retrieveProductsBought(Long consumerId, Integer left, Integer right){
        List<ProductResponse> arr = new ArrayList<>();
        for (ProductEntity i : productRepository.findAllBought(consumerId)){
            List<byte[]> arrPictures = productPictureService.retrieveByProductId(i.getId());
            arr.add(new ProductResponse(i.getId(), arrPictures.size() == 0 ? null : arrPictures.get(0),
                    i.getTitle(), i.getCategoryTitle(), i.getCost()));
        }

        return arr.subList(Math.min(arr.size(), left), Math.min(arr.size(), right));
    }

    public Long retrieveNumberProductsBought(Long consumerId){
        return productRepository.countByBought(consumerId);
    }

    //this products user bought for him self
    public List<ProductResponse> retrieveProductsUserBought(Long consumerId, Integer left, Integer right){
        List<ProductResponse> arr = new ArrayList<>();
        for (ProductEntity i : productRepository.findAllProductsUserBought(consumerId)){
            List<byte[]> arrPictures = productPictureService.retrieveByProductId(i.getId());
            arr.add(new ProductResponse(i.getId(), arrPictures.size() == 0 ? null : arrPictures.get(0),
                    i.getTitle(), i.getCategoryTitle(), i.getCost()));
        }

        return arr.subList(Math.min(arr.size(), left), Math.min(arr.size(), right));
    }

    public Long retrieveNumberProductsUserBought(Long consumerId){
        return productRepository.countByProductsUserBought(consumerId);
    }
}
