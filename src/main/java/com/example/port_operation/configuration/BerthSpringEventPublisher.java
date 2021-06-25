package com.example.port_operation.configuration;

import com.example.port_operation.model.Berth;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BerthSpringEventPublisher {
    private final Log log = LogFactory.getLog(BerthSpringEventPublisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public BerthSpringEventPublisher() {}

    public void publishBerthEvent(Berth berth) {
            boolean isFreeBerth = berth.isFreeBerth();
            if (isFreeBerth){
                log.info(String.format("Опубликовано событие по Причалу %s - причал пустой %s", berth, isFreeBerth));
                applicationEventPublisher.publishEvent(isFreeBerth);
            } else {
                log.info(String.format("Не Опубликовано событие по Причалу %s - причал занят", berth));
            }

    }
}
