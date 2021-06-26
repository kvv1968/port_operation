package com.example.port_operation.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeCargo typeCargo = (TypeCargo) o;
        return id == typeCargo.id && Objects.equals(type, typeCargo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "TypeCargo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
