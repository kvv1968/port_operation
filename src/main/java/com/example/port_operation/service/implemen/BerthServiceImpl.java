package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import com.example.port_operation.repository.interfaces.TypeCargoRepository;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
    private boolean isBerthsRun;

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
    @NotNull
    @Override
    public List<Berth> getAllBerths() {
        return Arrays.stream(berths).collect(Collectors.toList());
    }

    public void processBerth(Berth berth, Ship ship) throws ExecutionException, InterruptedException {
        logger.info(String.format("Пришвартовался на причал %s для выгрузки корабль %s",
                berth.getTypeCargo().getType(), ship));
        berth.setFreeBerth(false);
        raidService.removeShipByRaid(ship);
        ShipUnload shipUnload = new ShipUnload(ship);
        berth.setShipUnload(shipUnload);
        processThreads(shipUnload, berth);
    }

    @Override
    public boolean isFreeBerth(Berth berth) {
        return berth.isFreeBerth();
    }


    public Optional<Berth> processBerth(Ship ship) {
        Optional<Berth> optional = raidService.getShipsRaid().stream()
                .map(Ship::getCargo)
                .map(t -> {
                    return Arrays.stream(getBerths())
                            .filter(b -> b.getTypeCargo().getId() == t.getId())
                            .filter(b -> b.getShipUnload() == null)
                            .findAny();
                })
                .findAny().orElse(null);
        if (optional != null && ship.getAmountCargo() != 0) {
            logger.info(String.format("Выбран причал %s для выгрузки корабля %d", optional.orElse(null), ship.getId()));
        }
        return optional;
    }

    private void processThreads(ShipUnload shipUnload, Berth berth) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        logger.info(String.format("Запуск 2 потоков для разгрузки корабля %s", shipUnload.getShip()));

        Future<Thread> future1 = executorService.submit(shipUnload);
        Future<Thread> future2 = executorService.submit(shipUnload);
        berth.setThreadNames(String.format("%s  и  %s", future1.get().getId(), future2.get().getId()));
        berth.setFreeBerth(true);
        berth.setShipUnload(null);

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
    public void run() {
        Thread thread = Thread.currentThread();
        logger.info(String.format("Запуск потока %s на причалах", thread.getName()));
        while (isBerthsRun) {
            if (raidService.getRaid() != null){
                processBerths(raidService.getRaid());
                Thread.sleep(2000);
            }
        }
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull Raid raid) {
        logger.info(String.format("На причальном сервисе Услышали событие на рейде %s",raid));
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            processBerths(raid);
        }finally {
            lock.unlock();
        }
    }

    private void processBerths(@NotNull Raid raid){
        Optional<Berth> optionalBerth;
        for (Ship ship : raid.getShipsRaid()) {
            optionalBerth = processBerth(ship);
            if (optionalBerth.isPresent()){
                optionalBerth.ifPresent(berth -> {
                    try {
                        processBerth(berth, ship);
                    } catch (ExecutionException | InterruptedException e) {
                        logger.error(e);
                    }
                });
            }
        }
    }
}
