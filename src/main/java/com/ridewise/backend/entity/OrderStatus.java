package com.ridewise.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderStatus")
@Data
public class OrderStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders.id", nullable = false)
    private Order order;
    
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}