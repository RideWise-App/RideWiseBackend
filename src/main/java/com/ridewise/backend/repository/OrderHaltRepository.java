package com.ridewise.backend.repository;

import com.ridewise.backend.entity.OrderHalt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHaltRepository extends JpaRepository<OrderHalt, Long> {
}
