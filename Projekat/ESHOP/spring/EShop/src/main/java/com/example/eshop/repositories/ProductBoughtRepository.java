package com.example.eshop.repositories;

import com.example.eshop.models.entities.ProductBoughtEntity;
import com.example.eshop.models.entities.ProductBoughtEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoughtRepository extends JpaRepository<ProductBoughtEntity, Long> {
}
