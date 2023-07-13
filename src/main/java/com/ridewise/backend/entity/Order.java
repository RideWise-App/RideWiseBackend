package com.ridewise.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client.id", nullable = false)
    private Client client;

    private Long driverId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id")
    private Location startLocation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_location_id")
    private Location endLocation;
    private Boolean completed;
}