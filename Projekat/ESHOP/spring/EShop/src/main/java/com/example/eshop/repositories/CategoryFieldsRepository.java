package com.example.eshop.repositories;

import com.example.eshop.models.entities.CategoryFieldsEntity;
import com.example.eshop.models.entities.CategoryFieldsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFieldsEntity, CategoryFieldsEntityPK> {
    public List<CategoryFieldsEntity> findByCategoryTitle(String categoryTitle);
}
