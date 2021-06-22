package com.example.port_operation.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.Setter;


public class Berth  {
    @Getter
    private TypeCargo typeCargo;
    @Getter
    @Setter
    private List<Ship> ships = new CopyOnWriteArrayList<>();
    private volatile boolean isFreeBerth;
    @Getter
    @Setter
    private int unloadingSpeed;
    @Getter
    @Setter
    private String nameThread;
    @Getter
    @Setter
    private volatile int processIndicator;
    @Getter
    private List<Ship>shipsReports = new ArrayList<>();


    public Berth(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }


    public void run() {
        Ship ship = ships.get(0);
        for (int i = ship.getAmountCargo(); i > 0; i -= unloadingSpeed) {
            processIndicator = i;
        }
        if (processIndicator < 0){
            processIndicator = 0;
            ship.setAmountCargo(processIndicator);
            ships.remove(ship);
            shipsReports.add(ship);
        }
    }
    public synchronized boolean isFreeBerth(){
        isFreeBerth = processIndicator > 0;
        return isFreeBerth;
    }
    public void addShip(Ship ship){
        ships.add(ship);
    }
}
