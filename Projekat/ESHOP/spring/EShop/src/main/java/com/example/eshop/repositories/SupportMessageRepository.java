package com.example.eshop.repositories;

import com.example.eshop.models.entities.SupportMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface SupportMessageRepository extends JpaRepository<SupportMessageEntity, Long> {
}
