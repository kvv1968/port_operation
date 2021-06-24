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
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Data
@Transactional
public class ShipServiceImpl implements ShipService {
    private final Log logger = LogFactory.getLog(ShipServiceImpl.class);
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
        logger.info(String.format("Удалили из репозитория корабль %s", ship));
        shipRepository.delete(ship);
    }

    @Override
    public Ship updateShip(Ship ship) {
        logger.info(String.format("Обновили или сохранили в репозитории корабль %s", ship));
       return shipRepository.saveAndFlush(ship);
    }


    public  void saveTypeCargoRepo() {
        List<TypeCargo>typeCargos = Arrays.asList(new TypeCargo(1, "Легкий груз"),
                new TypeCargo(2, "Средний груз"), new TypeCargo(3, "Тяжелый груз"));
        logger.info(String.format("Записали в репозиторий TypeCargo %s", typeCargos));
        cargoRepository.saveAllAndFlush(typeCargos);
    }

    public Ship  shipGeneration(){
        Random random = new Random();
        TypeCargo typeCargo = cargoRepository.findTypeCargoById(random.nextInt(3) + 1);
        int amount = random.nextInt(Integer.MAX_VALUE);
        Ship ship = new Ship(typeCargo, amount);
        logger.info(String.format("Сгенерировали новый корабль %s", ship));
        return updateShip(ship);
    }

    @Override
    public void deleteAllRepoShips() {
        logger.info("Удалили из репозитория все корабли");
        shipRepository.deleteAll();
    }

}
