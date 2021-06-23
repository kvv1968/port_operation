package com.example.port_operation.model;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

@Getter
@Setter
public class Berth implements Runnable {
    private final Log logger = LogFactory.getLog(Berth.class);
    private TypeCargo typeCargo;

    private Ship ship;
    private boolean isFreeBerth;
    private int unloadingSpeed;
    private String nameThread;
    private volatile int processIndicator;
    private List<Ship> shipsReports = new ArrayList<>();

    public Berth(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }

    private Ship process(Ship ship) {
        logger.info(String.format("Началась выгрузка корабля %s", ship));
        int amount = ship.getAmountCargo();
        for (int i = amount; i > 0; i -= unloadingSpeed) {
            processIndicator = i;
            setProcessIndicator(processIndicator);
        }

        processIndicator = 0;
        ship.setAmountCargo(0);
        logger.info(String.format("Выгрузка корабля %s закончилась", ship));

        return ship;
    }

    public void isFreeBerth() {
        isFreeBerth = processIndicator > 0;
    }

    public void addShipByBerth(Ship ship) {
        this.setShip(ship);
    }


    @SneakyThrows
    @Override
    public void run() {
        if (!isFreeBerth) {
            Ship ship = process(getShip());
            shipsReports.add(ship);
            setShip(null);
            logger.info(String.format("Записан корабль %s в список отчетов %s и удален из списка причала %s",
                    ship, shipsReports, ship));
        }
        Thread.yield();
        logger.info(String.format("Поток %s работу закончил", Thread.currentThread()));
    }

    @Override
    public String toString() {
        return "Berth{" +
                "typeCargo=" + typeCargo +
                ", ship=" + ship +
                ", isFreeBerth=" + isFreeBerth +
                ", unloadingSpeed=" + unloadingSpeed +
                '}';
    }
}
