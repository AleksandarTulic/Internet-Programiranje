package com.example.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String title;
    private String description;
    private Double cost;
    private String productType;
    private String categoryTitle;
    private Long consumerId;
    private List<ProductFieldValues> fieldValues;
    private List<MultipartFile> pictures;
}
