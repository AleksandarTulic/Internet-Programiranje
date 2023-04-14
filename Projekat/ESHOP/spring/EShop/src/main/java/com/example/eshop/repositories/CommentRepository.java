package com.example.eshop.repositories;

import com.example.eshop.models.entities.CommentEntity;
import com.example.eshop.models.entities.CommentEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<CommentEntity, CommentEntityPK> {
    List<CommentEntity> findAllByProductId(Long productId);
}
