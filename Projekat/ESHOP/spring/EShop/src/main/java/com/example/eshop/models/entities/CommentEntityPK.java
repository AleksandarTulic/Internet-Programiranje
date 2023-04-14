package com.example.eshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CommentEntityPK implements Serializable {
    @Column(name = "product_id")
    @Id
    private Long productId;
    @Column(name = "consumer_id")
    @Id
    private Long consumerId;
    @Column(name = "datetime")
    @Id
    private Timestamp datetime;

}
