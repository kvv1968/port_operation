package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Ship;

public interface RaidService {
    boolean isFreeRaid(int capacity);
    boolean addShipByRaid(Ship ship);
}
