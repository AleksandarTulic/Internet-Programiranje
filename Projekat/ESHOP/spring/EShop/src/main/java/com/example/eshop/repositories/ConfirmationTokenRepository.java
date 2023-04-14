package com.example.eshop.repositories;

import com.example.eshop.models.entities.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Long> {
    Optional<ConfirmationTokenEntity> findByConsumerId(Long consumerId);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationTokenEntity c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.consumerId = ?1")
    int updateConfirmedAt(Long consumerId,
                          Timestamp confirmedAt);
}
