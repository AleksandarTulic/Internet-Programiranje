package com.example.eshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_fields", schema = "e_shop", catalog = "")
@IdClass(CategoryFieldsEntityPK.class)
public class CategoryFieldsEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_title")
    private String categoryTitle;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "field_type")
    private String fieldType;
    @Basic
    @Column(name = "regex")
    private String regex;

    @Basic
    @Column(name = "flag_fixed_multiple_values")
    private Byte flagFixedMultipleValues;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_title", referencedColumnName = "title", nullable = false, insertable=false, updatable=false)
    private CategoryEntity categoryEntity;
    @OneToMany(mappedBy = "categoryFieldEntity")
    @JsonIgnore
    private List<ProductFieldValuesEntity> productFieldValues;

}
