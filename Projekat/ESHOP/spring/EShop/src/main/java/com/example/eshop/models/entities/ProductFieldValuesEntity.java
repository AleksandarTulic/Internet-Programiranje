package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "product_field_values", schema = "e_shop", catalog = "")
@IdClass(ProductFieldValuesEntityPK.class)
@NoArgsConstructor
@AllArgsConstructor
public class ProductFieldValuesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_field_title")
    private String categoryFieldTitle;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_field_name")
    private String categoryFieldName;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Basic
    @Column(name = "field_value")
    private String fieldValue;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "category_field_title", referencedColumnName = "category_title", nullable = false, insertable=false, updatable=false), @JoinColumn(name = "category_field_name", referencedColumnName = "name", nullable = false, insertable=false, updatable=false)})
    private CategoryFieldsEntity categoryFieldEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private ProductEntity productEntity;

    public ProductFieldValuesEntity(String categoryFieldTitle, String categoryFieldName, Long productId, String fieldValue){
        this.categoryFieldName = categoryFieldName;
        this.categoryFieldTitle = categoryFieldTitle;
        this.productId = productId;
        this.fieldValue = fieldValue;
    }

}
