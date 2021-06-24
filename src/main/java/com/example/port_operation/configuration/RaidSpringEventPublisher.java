package com.example.port_operation.configuration;

import com.example.port_operation.model.Raid;
import lombok.Getter;
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
    @Getter
    private Raid raid;
    @Autowired
    public RaidSpringEventPublisher() {
      this.raid = new Raid(0);
    }

    public void publishRaidEvent(int raidCapacity) {
        raid.setRaidCapacity(raidCapacity);
        log.info(String.format("Опубликовано событие по Raid - вместимость кораблей на рейде %s",raidCapacity));
        applicationEventPublisher.publishEvent(raid);
    }


}
