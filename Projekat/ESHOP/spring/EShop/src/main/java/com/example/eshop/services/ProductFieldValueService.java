package com.example.eshop.services;

import com.example.eshop.models.entities.ProductFieldValuesEntity;
import com.example.eshop.repositories.ProductFieldValuesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductFieldValueService {
    private final ProductFieldValuesRepository productFieldValuesRepository;

    public List<ProductFieldValuesEntity> findByCategoryFieldTitleAndProductId(String categoryFieldTitle, Long productId){
        List<ProductFieldValuesEntity> arr = productFieldValuesRepository.findByCategoryFieldTitleAndProductId(categoryFieldTitle, productId);

        for (ProductFieldValuesEntity i : arr){
            i.getProductEntity().setConsumerEntity(null);
        }

        return arr;
    }
}
