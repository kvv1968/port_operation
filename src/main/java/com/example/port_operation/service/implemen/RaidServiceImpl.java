package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class RaidServiceImpl implements RaidService {
    private final Log logger = LogFactory.getLog(RaidServiceImpl.class);
    private ShipService shipService;
    private Raid raid;
    @Getter
    @Setter
    private List<Ship> shipsList;
    private int count = 0;

    public RaidServiceImpl(ShipService shipService) {
        this.shipService = shipService;
    }



    @Override
    public Raid getRaidCaparasity(int caparasity) {
        raid = Raid.getInstance(caparasity);
        return raid;
    }

    public List<Ship> getShips() {
        return shipsList;
    }

    @Override
    public void deleteShipRepo(Ship ship) {
        shipService.deleteShipRepo(ship);
    }

    @Override
    public void removeShipByRaid(Ship ship) {
        shipsList.remove(ship);
    }

    @Override
    public void deleteAllRepoShips() {
        shipService.deleteAllRepoShips();
    }

    @SneakyThrows
    @Override
    public void run() {
        logger.info("Поток запущен RaidService");
        shipsList = new CopyOnWriteArrayList<>();
        while (true){
            processRaid();
        }
    }

    private synchronized void processRaid() throws InterruptedException {
        while (raid.isFreeRaid(shipsList)){
            Ship ship = shipService.shipGeneration();
            shipsList.add(ship);
            count++;
            logger.info(String.format("Добавлен корабль %s в список рейда %s", ship, shipsList));
        }
        Thread.sleep(10000);
        logger.info("Рейд занят");
    }

    public int getCount() {
        return count;
    }
}
