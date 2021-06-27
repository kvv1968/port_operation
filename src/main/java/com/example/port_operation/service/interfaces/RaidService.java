package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.implemen.PortService;
import java.util.List;
import org.springframework.context.ApplicationListener;

public interface RaidService extends ApplicationListener<Raid>, Runnable {
    List<Ship> getShipsRaid();
    void removeShipByRaid(Ship ship);
    void deleteAllRepoShips();
    int getCount();
    void setRaid(Raid raid);
    void setPortService(PortService portService);
    Raid getRaid();
    boolean isRunRaid();
    void setRunRaid(boolean runRaid);
}
