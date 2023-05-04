package com.ridewise.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderHalt")
public class OrderHalt {
    
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "halt_location_id", nullable = false)
    private Location haltLocation;
}
