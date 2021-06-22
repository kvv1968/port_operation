package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import java.util.List;

public interface RaidService {
    void addShipByRaid();
    Raid getRaidCaparasity(int caparasity);
    void deleteShipByRaid(Ship ship);
    List<Ship> getShips();
    void updateAllShips(List<Ship>ships);
    Raid getRaid();
}
