package com.example.eshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFieldsEntityPK implements Serializable {
    @Column(name = "category_title")
    @Id
    private String categoryTitle;
    @Column(name = "name")
    @Id
    private String name;

}
