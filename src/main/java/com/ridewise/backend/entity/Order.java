package com.ridewise.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order")
@Data
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_driver", nullable = false)
    private Driver driver;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id", nullable = false)
    private Location startLocation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_location_id", nullable = false)
    private Location endLocation;
}