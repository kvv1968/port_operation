package com.example.port_operation.model;


public class Berth {
    private TypeCargo typeCargo;
    private boolean berthIsFree;
    private Ship ship;

    public Berth(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }

    //выгрузка на данном причале
    public void unloadingBerth() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = ship.getAmountCargo();
                while (i > 0) {
                    i -= 1000000;
                    System.out.printf("Идет выгрузка корабля %s осталось %d%n", ship, Math.max(i, 0));
                }
                Thread.yield();
                System.out.printf("Выгрузка корабля %s закончилась емкость -> %d%n", ship, 0);
                System.out.println("Поток ЗАКРЫТ!!!!!!!!!!!");
            }

        });
        thread.start();
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public TypeCargo getTypeCargo() {
        return typeCargo;
    }
}
