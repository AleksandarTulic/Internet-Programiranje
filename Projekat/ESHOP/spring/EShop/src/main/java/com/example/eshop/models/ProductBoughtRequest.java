package com.example.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBoughtRequest {
    private Long productId;
    private Long consumerId;
    private String paymentType;
}
