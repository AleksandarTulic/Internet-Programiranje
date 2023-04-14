package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category", schema = "e_shop", catalog = "")
public class CategoryEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "categoryEntity")
    @JsonIgnore
    private List<CategoryFieldsEntity> categoryFields;
    @OneToMany(mappedBy = "categoryEntity")
    @JsonIgnore
    private List<ProductEntity> products;

}
