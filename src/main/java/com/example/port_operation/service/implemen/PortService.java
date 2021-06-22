package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class PortService implements Runnable {
    private int raidCapacity;
    private int unloadingSpeed;

    private RaidService raidService;
    private BerthService berthService;
    private Raid raid;

    public PortService(RaidService raidService, BerthService berthService) {
        this.raidService = raidService;
        this.berthService = berthService;
    }

    public List<Ship> getAllShipsByRaid() {
        if (raid != null){
            return raid.getShips();
        }
        return null;
    }


    public List<Berth> getAllBerths() {
        return berthService.getAllBerths();
    }


    @Override
    public void run() {
        raid = raidService.getRaidCaparasity(raidCapacity);
        while (true){
            raidService.addShipByRaid();
            berthService.unloadingBerth(unloadingSpeed);
        }
    }
}
