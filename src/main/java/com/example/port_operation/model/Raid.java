package com.example.port_operation.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;

@Getter
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

    public void setShipList(List<Ship> shipList) {
        this.shipList.addAll(shipList);
    }
    public void removeRaid(Ship ship){
        shipList.remove(ship);
    }
}
