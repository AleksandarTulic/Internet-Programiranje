package com.example.eshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFieldValuesEntityPK implements Serializable {
    @Column(name = "category_field_title")
    @Id
    private String categoryFieldTitle;
    @Column(name = "category_field_name")
    @Id
    private String categoryFieldName;
    @Column(name = "product_id")
    @Id
    private Long productId;

}
