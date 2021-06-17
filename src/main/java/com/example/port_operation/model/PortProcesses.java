package com.example.port_operation.model;

public class PortProcesses {
    private Berth[] berths ;
    private Raid raid;
    private static int raidCapacity = 10;

    public PortProcesses() {
        this.berths = new Berth[]{
                new Berth(TypeCargo.LIGHT),
                new Berth(TypeCargo.HEAVY),
                new Berth(TypeCargo.MEDIUM)
        };
        this.raid = new Raid(raidCapacity);
    }

    //выгрузка груза на причале
    public void unloadingCargoBerth(Ship ship){
        TypeCargo typeCargo = ship.getCargo();
        Berth berth;
        for (Berth ber:berths){
            if (typeCargo == ber.getTypeCargo()) {

                System.out.println("Тип груза совпадает, причал свободный");
                System.out.printf("Удаляем корабль %s с рейда%n", ship);
                raid.getShipList().remove(ship);
                berth = ber;
                berth.setShip(ship);
                berth.unloadingBerth();
            }
        }
    }

    public Raid getRaid() {
        return raid;
    }
}
