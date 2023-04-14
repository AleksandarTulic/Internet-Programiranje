package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "admin", schema = "e_shop", catalog = "")
public class AdminEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long userId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userEntity;

}
