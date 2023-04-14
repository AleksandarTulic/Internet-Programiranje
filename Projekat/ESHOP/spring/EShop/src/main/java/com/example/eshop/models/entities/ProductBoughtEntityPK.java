package com.example.eshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
public class ProductBoughtEntityPK implements Serializable {
    @Column(name = "product_id")
    @Id
    private Long productId;
    @Column(name = "consumer_id")
    @Id
    private Long consumerId;

}
