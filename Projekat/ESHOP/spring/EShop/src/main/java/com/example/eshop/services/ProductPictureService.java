package com.example.eshop.services;

import com.example.eshop.models.entities.ProductPicturesEntity;
import com.example.eshop.repositories.ProductPicturesRepository;
import com.example.eshop.services.utilities.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductPictureService {
    private final ProductPicturesRepository productPicturesRepository;
    private final FileService fileService;

    public List<byte[]> retrieveByProductId(Long id){
        List<ProductPicturesEntity> arr = productPicturesRepository.findByProductId(id);
        List<byte[]> arrReturn = new ArrayList<>();

        for (ProductPicturesEntity i : arr)
            arrReturn.add(fileService.getFile(i.getProductEntity().getConsumerEntity().getUserEntity().getUsername() + File.separator + "products" + File.separator + id + File.separator + i.getName()));

        return arrReturn;
    }
}
