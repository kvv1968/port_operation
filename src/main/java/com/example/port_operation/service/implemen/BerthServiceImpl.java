package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.BerthSpringEventPublisher;
import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BerthServiceImpl implements BerthService {
    private final Log logger = LogFactory.getLog(BerthServiceImpl.class);
    private List<ShipUnload> shipsUnloadReports = new ArrayList<>();
    private RaidService raidService;
    private int index = 0;

    @Autowired
    private BerthSpringEventPublisher berthSpringEventPublisher;
    @Autowired
    private RaidSpringEventPublisher raidSpringEventPublisher;

    private Berth[] berths;

    public BerthServiceImpl(RaidService raidService, TypeCargoRepository cargoRepository) {
        this.raidService = raidService;
        this.berths = new Berth[] {
                new Berth(cargoRepository.findTypeCargoById(1), true),
                new Berth(cargoRepository.findTypeCargoById(2), true),
                new Berth(cargoRepository.findTypeCargoById(3), true)
        };

    }

    @Override
    public List<Berth> getAllBerths() {
        return Arrays.stream(berths).collect(Collectors.toList());
    }

    public synchronized void processBerth(Berth berth, List<Ship> shipsRaid) throws ExecutionException, InterruptedException {
        for (Ship ship : shipsRaid) {
            if (ship.getCargo().getId() == berth.getTypeCargo().getId() && berth.isFreeBerth()) {
                logger.info(String.format("Запуск потока на причале %s для корабля %s", berth, ship));
                berth.setFreeBerth(false);
                raidService.removeShipByRaid(ship);
                ShipUnload shipUnload = new ShipUnload(ship);
                berth.setShipUnload(shipUnload);
                processThreads(shipUnload, berth);
            }
        }
    }

    @Override
    public synchronized void processBerth(Ship ship) throws ExecutionException, InterruptedException {
        Berth berth = raidService.getShipsRaid().stream()
                .map(Ship::getCargo)
                .map(t -> {
                    return Arrays.stream(getBerths())
                            .filter(b -> b.getTypeCargo().getId() == t.getId())
                            .filter(b -> b.getShipUnload() == null)
                            .findAny()
                            .orElse(null);
                }).findAny().orElse(null);


        if (berth != null){
            processBerth(berth, raidService.getShipsRaid());
            logger.info(String.format("Добавлен корабль %s на причал %s", ship, berth));
        }
    }

    private void processThreads(ShipUnload shipUnload, Berth berth) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        logger.info(String.format("Запуск 2 потоков для разгрузки корабля %s", shipUnload.getShip()));

        Future<Thread> future1 = executorService.submit(shipUnload);
        Future<Thread> future2 = executorService.submit(shipUnload);
        berth.setThreadNames(String.format("%s  и  %s", future1.get().getId(), future2.get().getId()));
        berth.setFreeBerth(true);
        berth.setShipUnload(null);
        berthSpringEventPublisher.publishBerthEvent(berth);

        shipUnload.getShip().setAmountCargo(0);
        shipsUnloadReports.add(shipUnload);

        executorService.shutdown();
        logger.info(String.format("Остановка потоков по объекту выгрузки %s", shipUnload));
    }


    @Override
    public List<ShipUnload> shipUnloadReports() {
        return shipsUnloadReports;
    }


    public Berth[] getBerths() {
        return berths;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull Berth berth) {
        processBerth(berth, raidService.getShipsRaid());
    }

}
