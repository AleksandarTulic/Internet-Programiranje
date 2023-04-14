package com.example.eshop.repositories;

import com.example.eshop.models.entities.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Long> {
}
