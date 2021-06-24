package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import java.util.List;
import org.springframework.context.ApplicationListener;

public interface RaidService extends ApplicationListener<Raid> {
    List<Ship> getShipsRaid();
    void deleteShipRepo(Ship ship);
    void removeShipByRaid(Ship ship);
    void deleteAllRepoShips();
    int getCount();
}
