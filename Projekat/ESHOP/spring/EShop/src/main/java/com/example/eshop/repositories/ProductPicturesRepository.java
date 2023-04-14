package com.example.eshop.repositories;

import com.example.eshop.models.entities.ProductPicturesEntity;
import com.example.eshop.models.entities.ProductPicturesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductPicturesRepository extends JpaRepository<ProductPicturesEntity, ProductPicturesEntityPK> {
    void deleteByProductId(Long id);
    List<ProductPicturesEntity> findByProductId(Long productId);
}
