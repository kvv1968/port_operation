package com.example.port_operation.model;

import java.util.Arrays;
import java.util.Iterator;
import lombok.Data;

@Data
public class Raid {

    private int raidCapacity;
    private Ship[]ships = new Ship[raidCapacity];
    private static volatile Raid instance;

    public static Raid getInstance(int raidCapacity){
        if (instance == null) {
            instance = new Raid(raidCapacity);
        }
        return instance;
    }

    private Raid(int raidCapacity) {
        this.raidCapacity = raidCapacity;
    }


    public void removeRaid(Ship ship){
        Iterator<Ship>iterator = Arrays.stream(ships).iterator();
        while (iterator.hasNext()){
            Ship ship1 = iterator.next();
            if (ship.equals(ship1)){
                iterator.remove();
            }
        }
    }


}
