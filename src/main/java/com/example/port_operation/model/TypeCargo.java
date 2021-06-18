package com.example.port_operation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "typecargo")
public class TypeCargo {

    @Id
    private int id;

    @Column
    private String type;

    public TypeCargo(String type) {
        this.type = type;
    }

    public TypeCargo() {
    }
}
