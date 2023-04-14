package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "support_specialist", schema = "e_shop", catalog = "")
public class SupportSpecialistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long userId;
    @OneToMany(mappedBy = "supportSpecialistEntity")
    @JsonIgnore
    private List<SupportMessageEntity> supportMessages;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userEntity;

}
