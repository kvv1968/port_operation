package com.example.port_operation.model;


public class Berth {
    private TypeCargo typeCargo;
    private boolean berthIsFree;
    private Ship ship;

    public Berth(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public TypeCargo getTypeCargo() {
        return typeCargo;
    }
}
