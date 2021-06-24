package com.example.port_operation.model;

import java.util.concurrent.Callable;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

@Data
public class ShipUnload implements Callable<Long> {
    private final Log logger = LogFactory.getLog(ShipUnload.class);
    private Ship ship;
    private String workingHours;
    private int unloadingSpeed;

    public ShipUnload(Ship ship, int unloadingSpeed) {
        this.ship = ship;
        this.unloadingSpeed = unloadingSpeed;
    }

    @Override
    public Long call() {
        long startTime = System.currentTimeMillis();
          processUnload();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
    public String getWorkingHours(long millis) {
        long minutes = (millis / 1000)  / 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%d минут, %d секунд", minutes, seconds);
    }
    private void processUnload(){
        int amount = ship.getAmountCargo()/2;
        while (amount > 0){
            amount -= unloadingSpeed;
        }
    }

    @Override
    public String toString() {
        return "ShipUnload{" +
                "logger=" + logger +
                ", ship=" + ship +
                ", workingHours='" + workingHours + '\'' +
                ", unloadingSpeed=" + unloadingSpeed +
                '}';
    }
}
