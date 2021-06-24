package com.example.port_operation.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.Setter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class Raid extends ApplicationEvent {
    private final Log logger = LogFactory.getLog(Raid.class);
    private int raidCapacity;
    private List<Ship> shipsRaid = new CopyOnWriteArrayList<>();
    private boolean  isFreeRaid;



    public Raid(int raidCapacity) {
        super(raidCapacity);
        this.raidCapacity = raidCapacity;
    }

    public void addShipRaid(Ship ship){
        shipsRaid.add(ship);
    }

    public boolean isFreeRaid() {
        isFreeRaid = shipsRaid.size() < raidCapacity;
        logger.info(String.format("Рейд пустой %s", isFreeRaid));
        return isFreeRaid;
    }

}
