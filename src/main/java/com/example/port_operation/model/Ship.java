package com.example.port_operation.model;

import java.util.Random;
import lombok.Data;

@Data
public class Ship{
    private TypeCargo cargo;
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
