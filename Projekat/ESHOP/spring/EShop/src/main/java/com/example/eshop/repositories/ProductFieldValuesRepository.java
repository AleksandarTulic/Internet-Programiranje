package com.example.eshop.repositories;

import com.example.eshop.models.entities.ProductFieldValuesEntity;
import com.example.eshop.models.entities.ProductFieldValuesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFieldValuesRepository extends JpaRepository<ProductFieldValuesEntity, ProductFieldValuesEntityPK> {
    List<ProductFieldValuesEntity> findByCategoryFieldTitleAndProductId(String categoryFieldTitle, Long productId);
}
