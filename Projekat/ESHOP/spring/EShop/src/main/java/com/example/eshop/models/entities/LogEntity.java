package com.example.eshop.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log", schema = "e_shop", catalog = "")
public class LogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "level")
    private String level;
    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "consumer_id")
    private Long consumerId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consumer_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private ConsumerEntity consumerEntity;

    public LogEntity(Long id, String description, String level, Timestamp datetime, Long consumerId){
        this.id = id;
        this.description = description;
        this.level = level;
        this.datetime = datetime;
        this.consumerId = consumerId;
    }

}
