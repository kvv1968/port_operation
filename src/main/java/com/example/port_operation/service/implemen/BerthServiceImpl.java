package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.BerthSpringEventPublisher;
import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
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
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BerthServiceImpl implements BerthService  {
    private final Log logger = LogFactory.getLog(BerthServiceImpl.class);
    private List<ShipUnload> shipsUnloadReports = new ArrayList<>();
    private int unloadingSpeed;
    private RaidService raidService;
    private int index = 0;
    private Thread thread;
    private BerthSpringEventPublisher berthSpringEventPublisher;
    private RaidSpringEventPublisher raidSpringEventPublisher;

    public BerthServiceImpl(RaidService raidService,
                            BerthSpringEventPublisher berthSpringEventPublisher, RaidSpringEventPublisher raidSpringEventPublisher) {
        this.raidService = raidService;
        this.berthSpringEventPublisher = berthSpringEventPublisher;
        this.raidSpringEventPublisher = raidSpringEventPublisher;
    }

    @Override
    public List<Berth> getAllBerths() {
        return Arrays.stream(getBerths()).collect(Collectors.toList());
    }

    public synchronized void processBerth(Berth berth, Raid raid) throws ExecutionException, InterruptedException {
        List<Ship> shipsRaid = raid.getShipsRaid();
        for (Ship ship : shipsRaid) {
            if (ship.getCargo().getId() == berth.getTypeCargo().getId() && berth.isFreeBerth()) {
                logger.info(String.format("Запуск потока на причале %s для корабля %s", berth, ship));
                berth.setFreeBerth(false);
                raidService.removeShipByRaid(ship);
                ShipUnload shipUnload = new ShipUnload(ship, unloadingSpeed);
                berth.setShipUnload(shipUnload);
                processThreads(shipUnload, berth);
            }
        }
    }

    private void processThreads(ShipUnload shipUnload, Berth berth) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        logger.info(String.format("Запуск 2 потоков для разгрузки корабля %s", shipUnload.getShip()));

        Future<Long> future1 = executorService.submit(shipUnload);
        Future<Long> future2 = executorService.submit(shipUnload);

        String time = shipUnload.getWorkingHours(future1.get() + future2.get());
        shipUnload.setWorkingHours(time);
        shipUnload.getShip().setAmountCargo(0);
        shipsUnloadReports.add(shipUnload);
        berth.setFreeBerth(true);
        berth.setShipUnload(null);
        executorService.shutdown();
        logger.info(String.format("Остановка потоков по объекту выгрузки %s", shipUnload));
    }


    @Override
    public List<ShipUnload> shipUnloadReports() {
        return shipsUnloadReports;
    }


    public Berth[] getBerths() {
        return berthSpringEventPublisher.getBerths();
    }

    public void setBerths(Berth[] berths) {
        berthSpringEventPublisher.setBerths(berths);
    }


    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull Berth berth) {
        processBerth(berth, raidSpringEventPublisher.getRaid());
    }
}
