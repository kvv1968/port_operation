package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Data
public class RaidServiceImpl implements RaidService {
    private final Log logger = LogFactory.getLog(RaidServiceImpl.class);
    private ShipService shipService;
    private Raid raid;
    private int count = 0;
    private Thread thread;

    public RaidServiceImpl(ShipService shipService) {
        this.shipService = shipService;
    }


    @Override
    public List<Ship> getShipsRaid() {
        return raid.getShipsRaid();
    }

    @Override
    public void deleteShipRepo(Ship ship) {
        shipService.deleteShipRepo(ship);
    }

    @Override
    public void removeShipByRaid(Ship ship) {
        raid.getShipsRaid().remove(ship);
    }

    @Override
    public void deleteAllRepoShips() {
        shipService.deleteAllRepoShips();
    }


    public int getCount() {
        return count;
    }


    @Override
    public void onApplicationEvent(@NotNull Raid raid) {
        this.raid = raid;
        logger.info(String.format("Произошло событие на рейде стоит %s кораблей", raid.getShipsRaid().size()));
        while (raid.isFreeRaid()) {
            Ship ship = shipService.shipGeneration();
            raid.addShipRaid(ship);
            count++;
            logger.info(String.format("Добавлен корабль %s в список рейда %s", ship, raid.getShipsRaid()));
        }
    }
}
