package com.example.eshop.services;

import com.example.eshop.models.entities.CategoryFieldsEntity;
import com.example.eshop.repositories.CategoryFieldsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryFieldsService {
    private CategoryFieldsRepository categoryFieldsRepository;

    public List<CategoryFieldsEntity> findByCategoryTitle(String categoryTitle){
        return categoryFieldsRepository.findByCategoryTitle(categoryTitle);
    }
}
