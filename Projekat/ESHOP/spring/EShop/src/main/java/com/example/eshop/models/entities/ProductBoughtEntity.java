package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "product_bought", schema = "e_shop", catalog = "")
@AllArgsConstructor
@NoArgsConstructor
public class ProductBoughtEntity {
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Basic
    @Column(name = "consumer_id")
    private Long consumerId;
    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "payment_type")
    private String paymentType;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private ConsumerEntity consumerEntity;

    public ProductBoughtEntity(Long productId, Long consumerId, Timestamp datetime, String paymentType){
        this.productId = productId;
        this.consumerId = consumerId;
        this.datetime = datetime;
        this.paymentType = paymentType;
    }

}
