package com.example.port_operation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;

@Data
public class Raid {

    private int raidCapacity;
    private List<Ship> ships ;
    boolean isFreeRaid;

    private static volatile Raid instance;

    public static Raid getInstance(int raidCapacity){
        if (instance == null) {
            instance = new Raid(raidCapacity);
        }
        return instance;
    }

    private Raid(int raidCapacity) {
        this.raidCapacity = raidCapacity;
        ships = new ArrayList<>(raidCapacity);
    }


    public void removeRaid(Ship ship){
       ships.remove(ship);
    }
    public boolean isFreeRaid(){
        int count = (int)ships.stream().filter(Objects::nonNull).count();
        isFreeRaid = count < raidCapacity;
        return isFreeRaid;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
