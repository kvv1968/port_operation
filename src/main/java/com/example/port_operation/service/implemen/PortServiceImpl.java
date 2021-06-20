package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.TypeCargo;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.PortService;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PortServiceImpl implements PortService {

    private TypeCargoRepository cargoRepository;
    private RaidService raidService;
    private  BerthService berthService;
    private ShipService shipService;
    private List<Berth> berths;


    @Override
    public void unloadingShipByBerth(Ship ship, int unloadingSpeed) {
        Berth berth = getBerth(ship);
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            berthService.unloadingBerth(ship, unloadingSpeed);

        } finally {
            lock.unlock();
        }
    }


    private Berth getBerth(Ship ship) {
        return berths.stream()
                .filter(a-> a.getTypeCargo().equals(ship.getCargo()))
                .iterator().next();
    }

    @Override
    public Raid getRaid(int caparasity) {
        return Raid.getInstance(caparasity);
    }

    @Override
    public List<Berth> getBerthsRepoTypeCargo() {
        return cargoRepository.findAll().stream().map(Berth::new).collect(Collectors.toList());
    }

    public void addTypeCargo(String[]types) {
        if (cargoRepository.count() > 0){
            cargoRepository.deleteAll();
        }
        Arrays.stream(types).forEach(a -> cargoRepository.saveAndFlush(new TypeCargo(a)));
    }

    public ShipService getShipService() {
        return shipService;
    }

    @Override
    public List<Berth> getBerths() {
        return berths;
    }
    @Override
    public RaidService getRaidService() {
        return raidService;
    }
}
