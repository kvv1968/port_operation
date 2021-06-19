package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Raid;

public interface RaidService {
    boolean isFreeRaid();
    void addShipByRaid();
    Raid getRaid();
}
