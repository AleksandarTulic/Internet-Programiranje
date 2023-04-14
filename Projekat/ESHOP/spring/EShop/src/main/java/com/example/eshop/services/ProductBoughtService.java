package com.example.eshop.services;

import com.example.eshop.exceptions.NotFoundException;
import com.example.eshop.exceptions.ValidationFailedException;
import com.example.eshop.models.ProductBoughtRequest;
import com.example.eshop.models.entities.ProductBoughtEntity;
import com.example.eshop.models.entities.ProductEntity;
import com.example.eshop.repositories.ProductBoughtRepository;
import com.example.eshop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductBoughtService {
    private final ProductBoughtRepository productBoughtRepository;
    private final ProductRepository productRepository;
    private final LogService logService;

    public Boolean existsByProductId(Long productId){
        return productBoughtRepository.findById(productId).isPresent();
    }

    public ProductBoughtEntity save(ProductBoughtRequest productBoughtRequest){
        Optional<ProductEntity> productEntity = productRepository.findById(productBoughtRequest.getProductId());

        if (productEntity.isPresent()){
            if (productBoughtRequest.getConsumerId() != productEntity.get().getConsumerId()){
                ProductBoughtEntity pbe = new ProductBoughtEntity(productBoughtRequest.getProductId(), productBoughtRequest.getConsumerId(), Timestamp.valueOf(LocalDateTime.now()), productBoughtRequest.getPaymentType());
                return productBoughtRepository.save(pbe);
            }else{
                logService.save("Class: ProductBoughtService. Method: save. Reason: You are trying to buy your own product", LogService.LEVEL_DANGER, null);
                throw new ValidationFailedException("You are trying to buy your own product");
            }
        }else{
            logService.save("Class: ProductBoughtService. Method: save. Reason: Product with product_id=" + productBoughtRequest.getProductId() + " does not exist.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Product with product_id=" + productBoughtRequest.getProductId() + " does not exist.");
        }
    }
}
