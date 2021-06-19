package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.PortService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortServiceImpl implements PortService {

    private TypeCargoRepository cargoRepository;
    private final List<Berth> berths ;
    private RaidService raidService;
    private  BerthService berthService;

    @Autowired
    public PortServiceImpl(TypeCargoRepository cargoRepository, RaidService raidService,
                           BerthService berthService) {
        this.cargoRepository = cargoRepository;
        this.raidService = raidService;
        berths = cargoRepository.findAll().stream().map(Berth::new).collect(Collectors.toList());
        this.berthService = berthService;
    }
    public void unloadingShipByBerth(Ship ship){
        String typeCargoShip = ship.getCargo().getType();
        Berth berth = berths.stream()
                .filter(a-> a.getTypeCargo().getType().equals(typeCargoShip))
                .iterator().next();
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            berthService.unloadingBerth(ship);
        } finally {
            lock.unlock();
        }
    }
}
