package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import java.util.List;

public interface RaidService extends Runnable {
    Raid getRaidCaparasity(int caparasity);
    List<Ship> getShips();
    void deleteShipRepo(Ship ship);
    void removeShipByRaid(Ship ship);
    void deleteAllRepoShips();
    int getCount();
}
