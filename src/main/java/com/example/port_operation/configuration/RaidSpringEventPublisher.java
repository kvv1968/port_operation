package com.example.port_operation.configuration;

import com.example.port_operation.model.Raid;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RaidSpringEventPublisher {
    private final Log log = LogFactory.getLog(RaidSpringEventPublisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RaidSpringEventPublisher() {
    }

    public void publishRaidEvent(Raid raid) {
        int raidCapacity = raid.getRaidCapacity();
        int size = raid.getShipsRaid().size();
        boolean isFreeRaid = raid.isFreeRaid();
        if (isFreeRaid && size < raidCapacity) {
            log.info(String.format("Опубликовано событие по Raid - рейд свободен %s", isFreeRaid));
            applicationEventPublisher.publishEvent(raid);
        } else if (!isFreeRaid) {
            log.info(String.format("Не опубликовано событие по Raid - рейд свободен %s", isFreeRaid));
//        } else  if (size > 0){
//            log.info(String.format("Опубликовано событие по Raid - на рейде появились корабли %s", raid.getShipsRaid()));
//            applicationEventPublisher.publishEvent(raid);
        }else {
            log.info(String.format("Не опубликовано событие по Raid -  количество кораблей = вместимости рейда  %s",
                    raid.getShipsRaid().size()));
        }
    }

}
