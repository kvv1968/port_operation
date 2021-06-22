package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Ship;
import com.example.port_operation.model.TypeCargo;
import com.example.port_operation.repository.interfaces.ShipRepository;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Data
@Transactional
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;
    private TypeCargoRepository cargoRepository;

    public ShipServiceImpl(ShipRepository shipRepository,
                           TypeCargoRepository cargoRepository) {
        this.shipRepository = shipRepository;
        this.cargoRepository = cargoRepository;
        this.saveTypeCargoRepo();
    }

    @Override
    public List<Ship> getAllShipsRepo() {
        return shipRepository.findAll();
    }


    @Override
    public void deleteShipRepo(Ship ship) {
        shipRepository.delete(ship);
    }

    @Override
    public Ship updateShip(Ship ship) {
       return shipRepository.saveAndFlush(ship);
    }


    public  void saveTypeCargoRepo() {
        cargoRepository.saveAllAndFlush(Arrays.asList(new TypeCargo(1, "Легкий груз"),
                new TypeCargo(2, "Средний груз"), new TypeCargo(3, "Тяжелый груз")));
    }

    public Ship  shipGeneration(){
        Random random = new Random();
        TypeCargo typeCargo = cargoRepository.findTypeCargoById(random.nextInt(3) + 1);
        int amount = random.nextInt(Integer.MAX_VALUE);
        return updateShip(new Ship(typeCargo, amount));
    }

}
