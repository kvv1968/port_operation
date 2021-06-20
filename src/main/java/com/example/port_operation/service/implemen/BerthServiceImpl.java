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
    private Berth berth;
    private Raid raid;

    @Autowired
    public BerthServiceImpl(RaidService raidService) {
        this.raidService = raidService;
    }

    @Override
    public void unloadingBerth(Ship ship, int unloadingSpeed) {
        deleteShipDyRaid(ship);
        berth.setShip(ship);
        int amountCargo = ship.getAmountCargo();
        while (amountCargo > 0){
            amountCargo -= unloadingSpeed;
        }
        berth.setShip(null);
    }

    @Override
    public boolean berthIsFree(Ship ship) {

        return false;
    }

    private void deleteShipDyRaid(Ship ship){
        raid = raidService.getRaid();
        raid.removeRaid(ship);
    }


}
