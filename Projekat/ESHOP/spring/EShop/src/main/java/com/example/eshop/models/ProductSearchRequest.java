package com.example.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequest {;
    private String productTitle;
    private String categoryTitle;
    private String description;
    private String productType;
    private Double lowerCost;
    private Double upperCost;
    private String city;
    private Integer left;
    private Integer right;
}
