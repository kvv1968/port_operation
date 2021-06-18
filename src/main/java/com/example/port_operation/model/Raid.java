package com.example.port_operation.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Data;

@Data
public class Raid {

    private int raidCapacity;
    private List<Ship>shipList = new CopyOnWriteArrayList<>();
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


    // прием кораблей на рейд
    public boolean addShipByRaid(Ship ship){
        System.out.printf("Корабль %s встал на рейд.%n", ship);
        shipList.add(ship);
        return false;
    }
    public List<Ship> getShipList() {
        return shipList;
    }

}
