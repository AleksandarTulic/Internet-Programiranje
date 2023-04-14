package com.example.eshop.services;

import com.example.eshop.models.entities.CategoryEntity;
import com.example.eshop.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> findAll(){
        return categoryRepository.findAll();
    }
}
