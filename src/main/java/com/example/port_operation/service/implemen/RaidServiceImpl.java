package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class RaidServiceImpl implements RaidService {
    private final Log logger = LogFactory.getLog(RaidServiceImpl.class);
    private ShipService shipService;
    private Raid raid;
    private int count = 0;
    private boolean isRunRaid;

    private PortService portService;

    @Autowired
    private RaidSpringEventPublisher raidSpringEventPublisher;
    @Autowired
    private BerthService berthService;

    public RaidServiceImpl(ShipService shipService) {
        this.shipService = shipService;
    }


    @Override
    public List<Ship> getShipsRaid() {
        return raid.getShipsRaid();
    }


    public void deleteShipRepo(Ship ship) {
        shipService.deleteShipRepo(ship);
    }


    public void removeShipByRaid(Ship ship) {
        logger.info(String.format("Удалили корабль %d с рейда", ship.getId()));
        raid.getShipsRaid().remove(ship);
    }

    @Override
    public void deleteAllRepoShips() {
        shipService.deleteAllRepoShips();
    }


    public int getCount() {
        return count;
    }


    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull Raid raid) {
        logger.info(String.format("На рейдерном сервиса Услышали событие на рейде %s",raid));
        processingRaid();
    }

    private void processingRaid()  {
        logger.info(String.format("Произошло событие на рейде стоит %s кораблей", raid.getShipsRaid().size()));
        while (raid.getShipsRaid().size() < raid.getRaidCapacity()) {
            Ship ship = shipService.shipGeneration();
            raid.addShipRaid(ship);
            deleteShipRepo(ship);
            count++;
        }
    }


    @SneakyThrows
    @Override
    public void run()  {
        Thread thread = Thread.currentThread();
        logger.info(String.format("Запуск потока %s на рейде %s",thread.getName(),raid));
        while (isRunRaid){
            if (!raid.isFreeRaid()){
                thread.interrupt();
                logger.info(String.format("Поток %s остановлен из-за заполнение рейда", thread.getName()));
            } else if (Thread.interrupted()){
                logger.info(String.format("Поток %s продолжил работать", thread.getName()));
                raidSpringEventPublisher.publishRaidEvent(raid);
            }else {
                raidSpringEventPublisher.publishRaidEvent(raid);
            }
            Thread.sleep(2000);
        }
    }

}
