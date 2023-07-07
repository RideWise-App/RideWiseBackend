package com.ridewise.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;
    
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;
}