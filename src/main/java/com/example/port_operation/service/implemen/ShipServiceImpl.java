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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;
    private TypeCargoRepository cargoRepository;
    private RaidService raidService;
    private int numberCargoTypes;

    @Autowired
    public ShipServiceImpl(){
    }

    @Override
    public List<Ship> getAllShipsRaid() {
        return shipRepository.findAll();
    }

    @Override
    public void addShipRepo(int caparasity) {
        int index = caparasity;
        while (index > 0){
            shipRepository.save(shipGeneration());
            index--;
        }
    }

    @Override
    public void deleteShip(Ship ship) {
        shipRepository.delete(ship);
    }

    private Ship  shipGeneration(){
        Random random = new Random();
        TypeCargo typeCargo = cargoRepository.getTypeCargoById(random.nextInt(numberCargoTypes));
        int amount = random.nextInt(Integer.MAX_VALUE);
        return new Ship(typeCargo, amount);
    }
}
