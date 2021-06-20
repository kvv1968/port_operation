package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidServiceImpl implements RaidService {
    private int capacity;
    private ShipService shipService;
    private Raid raid;

    @Autowired
    public RaidServiceImpl(ShipService shipService) {
        this.shipService = shipService;
    }

    @Override
    public boolean isFreeRaid() {

        return false;
    }

    @Override
    public void addShipByRaid(int raidCapacity) {
        List<Ship>listRepo = shipService.getAllShipsRaid();//из репо получаем список кораблей
        Ship[]ships = raid.getShips();//список кораблей на рейде
        for (Ship ship:listRepo){
            for (int i = ships.length; i < raidCapacity; i++) {
                if (!ships[i].equals(ship)){
                    ships[i] = ship;
                }
            }
        }
    }

    @Override
    public Raid getRaid() {
        return raid;
    }


}
