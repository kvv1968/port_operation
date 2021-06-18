package com.example.port_operation.repository.interfaces;

import com.example.port_operation.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
