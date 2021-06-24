package com.example.port_operation.configuration;

import com.example.port_operation.model.Berth;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Data
public class BerthSpringEventPublisher {
    private final Log log = LogFactory.getLog(BerthSpringEventPublisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private Berth[] berths;
    @Autowired
    public BerthSpringEventPublisher(TypeCargoRepository cargoRepository) {
        this.berths = new Berth[] {
                new Berth(cargoRepository.findTypeCargoById(1), false),
                new Berth(cargoRepository.findTypeCargoById(2), false),
                new Berth(cargoRepository.findTypeCargoById(3), false)
        };
    }

    public void publishBerthEvent(boolean isFreeBerth) {
        for (Berth berth:berths){
            berth.setFreeBerth(isFreeBerth);
            log.info(String.format("Опубликовано событие по Причалу %s - причал пустой %s",berth, isFreeBerth));
            applicationEventPublisher.publishEvent(isFreeBerth);
        }
    }

}
