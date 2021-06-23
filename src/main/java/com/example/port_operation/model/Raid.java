package com.example.port_operation.model;

import java.util.List;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

@Data
public class Raid {
    private final Log logger = LogFactory.getLog(Raid.class);
    private int raidCapacity;
    private List<Ship> ships ;
    private volatile boolean  isFreeRaid;

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


    public void removeRaid(Ship ship){
       ships.remove(ship);
    }

    public boolean isFreeRaid(List<Ship> shipsList) {
        isFreeRaid = shipsList.size() < raidCapacity;
        logger.info(String.format("Рейд пустой %s", isFreeRaid));
        return isFreeRaid;
    }


}
