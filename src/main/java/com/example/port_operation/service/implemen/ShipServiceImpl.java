package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Ship;
import com.example.port_operation.model.TypeCargo;
import com.example.port_operation.repository.interfaces.ShipRepository;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;
    private TypeCargoRepository cargoRepository;
    private RaidService raidService;

    @Override
    public List<Ship> getAllShipsRaid() {
        return shipRepository.findAll();
    }

    @Override
    public boolean addShip(int capacity) {
        if (raidService.isFreeRaid(capacity)){
            shipRepository.save(shipGeneration());
            return true;
        }
        return false;
    }

    @Override
    public void deleteShip(Ship ship) {
        shipRepository.delete(ship);
    }

    private Ship  shipGeneration(){
        Random random = new Random();
        TypeCargo typeCargo = cargoRepository.getTypeCargoById(random.nextInt(3));
        int amount = random.nextInt(Integer.MAX_VALUE);
        return new Ship(typeCargo, amount);
    }
}
