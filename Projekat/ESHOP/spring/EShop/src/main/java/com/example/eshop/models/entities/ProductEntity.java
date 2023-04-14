package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name = "product", schema = "e_shop", catalog = "")
@NoArgsConstructor
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "cost")
    private Double cost;
    @Basic
    @Column(name = "product_type")
    private String productType;
    @Basic
    @Column(name = "category_title")
    private String categoryTitle;
    @Basic
    @Column(name = "consumer_id")
    private Long consumerId;
    @OneToMany(mappedBy = "productEntity")
    @JsonIgnore
    private List<CommentEntity> comments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_title", referencedColumnName = "title", nullable = false, insertable=false, updatable=false)
    private CategoryEntity categoryEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private ConsumerEntity consumerEntity;
    @OneToOne(mappedBy = "productEntity")
    @JsonIgnore
    private ProductBoughtEntity productBoughtEntity;
    @OneToMany(mappedBy = "productEntity")
    @JsonIgnore
    private List<ProductFieldValuesEntity> productFieldValues;
    @OneToMany(mappedBy = "productEntity")
    @JsonIgnore
    private List<ProductPicturesEntity> productPictures;

    public ProductEntity(Long id, String title, String description, Double cost, String productType, String categoryTitle, Long consumerId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.productType = productType;
        this.categoryTitle = categoryTitle;
        this.consumerId = consumerId;
    }

}
