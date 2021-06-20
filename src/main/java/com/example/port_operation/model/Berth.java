package com.example.port_operation.model;


public class Berth {
    private TypeCargo typeCargo;
    private Ship ship;

    public Berth(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }

    public TypeCargo getTypeCargo() {
        return typeCargo;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        if(typeCargo.equals(ship.getCargo())){
            this.ship = ship;
        }
    }
}
