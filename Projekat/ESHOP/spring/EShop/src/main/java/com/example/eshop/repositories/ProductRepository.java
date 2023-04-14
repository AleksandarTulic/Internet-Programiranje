package com.example.eshop.repositories;

import com.example.eshop.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Transactional
    @Query("select p from ProductEntity as p where p.consumerId=?1 and p.id not in (select pb.productId from ProductBoughtEntity as pb)")
    List<ProductEntity> findAllNotBought(Long id);

    @Transactional
    @Query("select count(p) from ProductEntity as p where p.consumerId=?1 and p.id not in (select pb.productId from ProductBoughtEntity as pb)")
    Long countByNotBought(Long id);

    @Transactional
    @Query("select p from ProductEntity as p where p.consumerId=?1 and p.id in (select pb.productId from ProductBoughtEntity as pb)")
    List<ProductEntity> findAllBought(Long id);

    @Transactional
    @Query("select count(p) from ProductEntity as p where p.consumerId=?1 and p.id in (select pb.productId from ProductBoughtEntity as pb)")
    Long countByBought(Long id);

    @Transactional
    @Query("select p from ProductEntity as p where p.id in (select pb.productId from ProductBoughtEntity as pb where pb.consumerId=?1)")
    List<ProductEntity> findAllProductsUserBought(Long id);

    @Transactional
    @Query("select count(p) from ProductEntity as p where p.id in (select pb.productId from ProductBoughtEntity as pb where pb.consumerId=?1)")
    Long countByProductsUserBought(Long id);
}
