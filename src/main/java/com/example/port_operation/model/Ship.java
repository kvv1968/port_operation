package com.example.port_operation.model;

import java.util.Objects;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private TypeCargo cargo;
    @Column
    private int amountCargo;

    public Ship(TypeCargo cargo, int amountCargo) {
        this.cargo = cargo;
        this.amountCargo = amountCargo;
    }


    public static Ship shipGeneration(){
        Random random = new Random();
        int typeCargoOrdinal = random.nextInt(3);
        int amount = random.nextInt(Integer.MAX_VALUE);
        return new Ship(TypeCargo.values()[typeCargoOrdinal], amount);
    }
}
