package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.ArrayList;
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
        this.raid = Raid.getInstance(capacity);
    }

    @Override
    public boolean isFreeRaid() {
        int size = raid.getShipList().size();
        return size < capacity;
    }

    @Override
    public void addShipByRaid() {
        List<Ship>listRepo = shipService.getAllShipsRaid();
        List<Ship>shipList = raid.getShipList();
        List<Ship>listNew = new ArrayList<>();
        for (Ship ship:listRepo){
            if (!(shipList.contains(ship)) && (shipList.size() < capacity)){
                listNew.add(ship);
                shipService.deleteShip(ship);
            }
        }
        if (!listNew.isEmpty()){
            raid.setShipList(listNew);
        }
    }

    @Override
    public Raid getRaid() {
        return raid;
    }


}
