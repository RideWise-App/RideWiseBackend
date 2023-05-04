package com.ridewise.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "location")
@Data
public class Location {
    
    @Id
    private Long id;
    
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;
    
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;
}