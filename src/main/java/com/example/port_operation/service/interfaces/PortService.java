package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import java.util.List;

public interface PortService {
    Raid getRaid(int caparasity);
    void unloadingShipByBerth(Ship ship, int unloadingSpeed);
    void addTypeCargo(String[]type);
    List<Berth> getBerthsRepoTypeCargo();
    ShipService getShipService();
    List<Berth> getBerths();
    RaidService getRaidService();
}
