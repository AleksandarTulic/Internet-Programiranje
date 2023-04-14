package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "support_message", schema = "e_shop", catalog = "")
public class SupportMessageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "message")
    private String message;
    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "flag_read")
    private Byte flagRead;
    @Basic
    @Column(name = "return_message")
    private String returnMessage;
    @Basic
    @Column(name = "consumer_id")
    private Long consumerId;
    @Basic
    @Column(name = "support_specialist_id")
    private Long supportSpecialistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private ConsumerEntity consumerEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_specialist_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private SupportSpecialistEntity supportSpecialistEntity;

    public SupportMessageEntity(String title, String message, Long consumerId) {
        this.title = title;
        this.message = message;
        this.consumerId = consumerId;
        this.datetime = Timestamp.valueOf(LocalDateTime.now());
        this.flagRead = 0;
    }
}
