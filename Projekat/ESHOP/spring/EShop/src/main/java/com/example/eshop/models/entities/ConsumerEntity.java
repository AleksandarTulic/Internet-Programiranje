package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "consumer", schema = "e_shop", catalog = "")
@NoArgsConstructor
public class ConsumerEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @OneToMany(mappedBy = "consumerEntity")
    @JsonIgnore
    private List<CommentEntity> comments;
    @OneToOne(mappedBy = "consumerEntity", fetch = FetchType.EAGER)
    private ConfirmationTokenEntity confirmationTokenEntity;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "consumerEntity")
    @JsonIgnore
    private List<LogEntity> logs;
    @OneToMany(mappedBy = "consumerEntity")
    @JsonIgnore
    private List<ProductEntity> products;
    @OneToMany(mappedBy = "consumerEntity")
    @JsonIgnore
    private List<ProductBoughtEntity> productsBought;
    @OneToMany(mappedBy = "consumerEntity")
    @JsonIgnore
    private List<SupportMessageEntity> supportMessages;

    public ConsumerEntity(Long userId, String email, String phone, String city, String avatar) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.avatar = avatar;
    }
}
