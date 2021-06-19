package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class BerthServiceImpl implements BerthService {

    private RaidService raidService;
    private int unloadingSpeed;

    @Autowired
    public BerthServiceImpl(RaidService raidService, int unloading) {
        this.raidService = raidService;
        this.unloadingSpeed = unloading;
    }

    @Override
    public void unloadingBerth(Ship ship) {
        Raid raid = raidService.getRaid();
        raid.removeRaid(ship);
        int amountCargo = ship.getAmountCargo();
        while (amountCargo > 0){
            amountCargo -= unloadingSpeed;
        }
    }
}
