package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BerthServiceImpl implements BerthService {
    private final Log logger = LogFactory.getLog(BerthServiceImpl.class);
    private Berth[] berths;
    private List<Ship> shipsReports = new ArrayList<>();
    private int unloadingSpeed;
    private RaidService raidService;
    private List<Thread> threads = new ArrayList<>();
    private int index = 0;

    public BerthServiceImpl(TypeCargoRepository cargoRepository, RaidService raidService) {
        this.raidService = raidService;
        this.berths = new Berth[] {
                new Berth(cargoRepository.findTypeCargoById(1)),
                new Berth(cargoRepository.findTypeCargoById(2)),
                new Berth(cargoRepository.findTypeCargoById(3))
        };
    }

    @Override
    public List<Berth> getAllBerths() {
        return Arrays.stream(berths).collect(Collectors.toList());
    }


    @SneakyThrows
    @Override
    public void run() {
        logger.info("Запущен поток BerthServiceImpl");
        while (true){
            List<Ship>ships = raidService.getShips();
            if (ships != null) {
                ships.forEach(ship -> {
                    if (ship.getAmountCargo() == 0) {
                        logger.info(String.format("Удаление корабля %s из списка рейда %s", ship, ships));
                        raidService.removeShipByRaid(ship);
                    }
                });
                processBerth(ships);
            }
            Thread.sleep(5000);
        }
    }

    private synchronized void processBerth(List<Ship> ships) {
        if (!ships.isEmpty()) {
            for (Ship ship : ships) {
                for (Berth berth : berths) {
                    if (berth.getShip()== null && ship.getCargo().equals(berth.getTypeCargo())) {
                        logger.info(String.format("Запуск потока на причале %s для корабля %s",berth, ship));
                        berth.setUnloadingSpeed(unloadingSpeed);
                        berth.setShip(ship);
                        berth.setNameThread(String.format("Поток причала %d, корабль %s", berth.getTypeCargo().getId(), ship));
                        Thread thread = new Thread(berth,
                                String.format("Поток причала %d", berth.getTypeCargo().getId()));
                        threads.add(index++, thread);
                        thread.start();
                        raidService.deleteShipRepo(ship);
                        shipsReports.add(ship);
                    } else {
                        logger.info(String.format("На причале %s список кораблей %s занят или не совпадает тип груза %s и %s",
                                berth, berth.getShip(), berth.getTypeCargo().getType(), ship.getCargo().getType()));
                    }

                }
            }
        }
    }

    @Override
    public void stopBehthsThreads() throws InterruptedException {
        for (Thread thread:threads){
            thread.interrupt();
            thread.join();
            logger.info(String.format("Остановлен поток %s", thread));
        }

    }

    @Override
    public List<Ship> shipsReports() {
        return shipsReports;
    }

    public void setUnloadingSpeed(int unloadingSpeed) {
        this.unloadingSpeed = unloadingSpeed;
    }
}
