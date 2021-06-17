package com.example.port_operation.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Data;

@Data
public class Raid {
    private int raidCapacity;
    private List<Ship>shipList = new CopyOnWriteArrayList<>();

    public Raid(int raidCapacity) {
        this.raidCapacity = raidCapacity;
    }
    //свободен рейд
    public boolean isFreeRaid(){
       return shipList.size() < 10;
    }

    // прием кораблей на рейд
    public void addShipByRaid(Ship ship){
        System.out.printf("Корабль %s встал на рейд.%n", ship);
        shipList.add(ship);
    }
    public List<Ship> getShipList() {
        return shipList;
    }

}
