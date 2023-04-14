package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_pictures", schema = "e_shop", catalog = "")
@IdClass(ProductPicturesEntityPK.class)
public class ProductPicturesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private Long productId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private ProductEntity productEntity;

    public ProductPicturesEntity(Long productId, String name){
        this.productId = productId;
        this.name = name;
    }

}
