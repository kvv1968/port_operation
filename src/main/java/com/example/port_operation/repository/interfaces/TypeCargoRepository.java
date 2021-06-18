package com.example.port_operation.repository.interfaces;

import com.example.port_operation.model.TypeCargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeCargoRepository extends JpaRepository<TypeCargo, Integer> {
    TypeCargo getTypeCargoById(int id);
}
