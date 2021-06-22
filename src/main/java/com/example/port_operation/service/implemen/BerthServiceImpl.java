package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BerthServiceImpl implements BerthService {

    private Berth[] berths;
    private List<Ship> shipsReport;

    private RaidService raidService;

    public BerthServiceImpl(TypeCargoRepository cargoRepository, RaidService raidService) {
        this.raidService = raidService;
        this.berths = new Berth[] {
                new Berth(cargoRepository.findTypeCargoById(1)),
                new Berth(cargoRepository.findTypeCargoById(2)),
                new Berth(cargoRepository.findTypeCargoById(3))
        };
    }

    @Override
    public synchronized void unloadingBerth(int unloadingSpeed) {
        List<Ship> shipList = shipsRaid();
        Berth berth ;
        for (Ship ship : shipList) {
            if (ship != null){
                berth = getBerth(ship);
                getRaid().removeRaid(ship);
                berth.addShip(ship);
                berth.setUnloadingSpeed(unloadingSpeed);
                if (berth.isFreeBerth()){
                    berth.run();
                }
            }
        }
    }


    public List<Ship> shipsRaid() {
        return raidService.getShips();
    }

    private Berth getBerth(Ship ship) {
        List<Berth>bert = Arrays.stream(berths).filter(a->a.getTypeCargo().equals(ship.getCargo())).collect(Collectors.toList());
        return bert.get(0);

    }

    public List<Berth> getAllBerths() {
        return Arrays.stream(berths).collect(Collectors.toList());
    }

    public Raid getRaid(){
        return raidService.getRaid();
    }


}
