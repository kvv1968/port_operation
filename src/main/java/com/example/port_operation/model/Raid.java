package com.example.port_operation.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Singleton
public class Raid extends ApplicationEvent {
    private final Log logger = LogFactory.getLog(Raid.class);

    private int raidCapacity;
    private List<Ship> shipsRaid;
    private boolean  isFreeRaid;


    public Raid(int raidCapacity, boolean  isFreeRaid) {
        super(isFreeRaid);
        this.raidCapacity = raidCapacity;
        this.isFreeRaid = isFreeRaid;
        shipsRaid = new CopyOnWriteArrayList<>();
    }

    public void addShipRaid(Ship ship){
        shipsRaid.add(ship);
    }

    public boolean isFreeRaid() {
        logger.info(String.format("Рейд свободен %s", isFreeRaid));
        return isFreeRaid;
    }

    public List<Ship> getShipsRaid() {
        return shipsRaid == null ? null : shipsRaid;
    }
}
