package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;

public class RaidServiceImpl implements RaidService {

    @Override
    public boolean isFreeRaid(int capacity) {
        Raid raid = Raid.getInstance(capacity);
        int size = raid.getShipList().size();
        return size < capacity;
    }

    @Override
    public boolean addShipByRaid(Ship ship) {
        return false;
    }
}