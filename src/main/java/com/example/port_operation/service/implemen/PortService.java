package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.BerthSpringEventPublisher;
import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.ReportPort;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.List;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Data
public class PortService implements ApplicationListener<Raid> {
    private final Log logger = LogFactory.getLog(PortService.class);
    private int raidCapacity;
    private int unloadingSpeed;

    private RaidService raidService;
    private BerthService berthService;
    private Raid raid;
    private long startProcessPort;
    private long endProcessPort;
    private boolean isRun = true;

    @Autowired
    private ApplicationContext applicationContext;

    public PortService(RaidService raidService,
                       BerthService berthService) {
        this.raidService = raidService;
        this.berthService = berthService;
    }

    public List<Ship> getAllShipsByRaid() {
        return raidService.getShipsRaid();
    }


    public List<Berth> getAllBerths() {
        return berthService.getAllBerths();
    }

    public List<ShipUnload> shipUnloadReports(){
        return berthService.shipUnloadReports();
    }

    public void processPort() throws InterruptedException {
        logger.info("Запуск процесса в порту");
        RaidSpringEventPublisher raidSpringEventPublisher = applicationContext.getBean(RaidSpringEventPublisher.class);
        BerthSpringEventPublisher berthSpringEventPublisher = applicationContext.getBean(BerthSpringEventPublisher.class);
        startProcessPort = System.currentTimeMillis();
        raidService.deleteAllRepoShips();
        berthService.setUnloadingSpeed(unloadingSpeed);
        process(raidSpringEventPublisher, berthSpringEventPublisher);
    }

    private void process(RaidSpringEventPublisher raidSpringEventPublisher,
                         BerthSpringEventPublisher berthSpringEventPublisher) throws InterruptedException {
        while (isRun){
            raidSpringEventPublisher.publishRaidEvent(raidCapacity);
            Thread.sleep(5000);
            berthSpringEventPublisher.publishBerthEvent(true);
            Thread.sleep(2000);
        }
    }

    public void stopProcessPort(){
        isRun = false;
        endProcessPort = System.currentTimeMillis();
        logger.info("Остановка процесса в порту");
    }

    public ReportPort getReport() {
        return new ReportPort(raidCapacity, unloadingSpeed,
                raidService.getCount(), berthService.shipUnloadReports().size(), String.valueOf(endProcessPort - startProcessPort),
                berthService.getAllBerths().size());
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull Raid raid) {
        for (Berth berth:getAllBerths()){
            berthService.processBerth(berth, raid);
        }
    }
}
