package com.example.port_operation.configuration;

//@Component
//public class BerthSpringEventPublisher {
//    private final Log log = LogFactory.getLog(BerthSpringEventPublisher.class);
//    @Autowired
//    private ApplicationEventPublisher applicationEventPublisher;
//
//    @Autowired
//    public BerthSpringEventPublisher() {}
//
//    public void publishBerthEvent(Berth berth) {
//            boolean isFreeBerth = berth.isFreeBerth();
//            if (isFreeBerth){
//                log.info(String.format("Опубликовано событие по Причалу %s - причал пустой %s", berth, isFreeBerth));
//                applicationEventPublisher.publishEvent(isFreeBerth);
//            } else {
//                log.info(String.format("Не Опубликовано событие по Причалу %s - причал занят", berth));
//            }
//
//    }
//}
