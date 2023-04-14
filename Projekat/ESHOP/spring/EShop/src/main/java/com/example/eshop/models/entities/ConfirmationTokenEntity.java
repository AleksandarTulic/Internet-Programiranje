package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "confirmation_token", schema = "e_shop", catalog = "")
@NoArgsConstructor
public class ConfirmationTokenEntity {
    @Id
    @Column(name = "consumer_id")
    private Long consumerId;
    @Basic
    @Column(name = "confirmed_at")
    private Timestamp confirmedAt;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "expires_at")
    private Timestamp expiresAt;
    @Basic
    @Column(name = "token")
    private Integer token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false)
    @JsonIgnore
    private ConsumerEntity consumerEntity;

    public ConfirmationTokenEntity(Long id, Timestamp confirmedAt, Timestamp createdAt, Timestamp expiresAt, Integer token) {
        this.consumerId = id;
        this.confirmedAt = confirmedAt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.token = token;
    }
}
