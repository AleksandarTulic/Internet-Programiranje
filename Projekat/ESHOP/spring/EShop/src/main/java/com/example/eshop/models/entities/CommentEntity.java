package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comment", schema = "e_shop", catalog = "")
@IdClass(CommentEntityPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private Long productId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "consumer_id")
    private Long consumerId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "value")
    private String value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private ConsumerEntity consumerEntity;

    public CommentEntity(Long productId, Long consumerId, Timestamp datetime, String value){
        this.productId = productId;
        this.consumerId = consumerId;
        this.datetime = datetime;
        this.value = value;
    }

}
