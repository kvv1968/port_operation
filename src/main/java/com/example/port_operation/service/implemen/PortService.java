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
import java.util.concurrent.ExecutionException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class PortService extends Thread {
    private final Log logger = LogFactory.getLog(PortService.class);
    private int raidCapacity;

    private RaidService raidService;
    private BerthService berthService;
    private Raid raid;
    private Berth[] berths;
    private long startProcessPort;
    private long endProcessPort;
    private boolean isRun = true;
    @Autowired
    private RaidSpringEventPublisher raidEventPublisher;
    @Autowired
    private BerthSpringEventPublisher berthEventPublisher;

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

    public void processPort() throws InterruptedException, ExecutionException {
        logger.info("Запуск процесса в порту");
        startProcessPort = System.currentTimeMillis();
        raidService.deleteAllRepoShips();
        raid = new Raid(raidCapacity, true);
        berths = berthService.getBerths();
        process();
    }

    private void process() throws InterruptedException, ExecutionException {
        while (isRun){
             raidEventPublisher.publishRaidEvent(raid);
            if (raid.getShipsRaid().size() != 0){
                for (Berth berth:berths){
                    berthService.processBerth(berth, raidService.getShipsRaid());
                }
            }
        }
    }

    public void stopProcessPort() throws InterruptedException {
        isRun = false;
        endProcessPort = System.currentTimeMillis();
        raidService.setRunRaid(false);
        this.interrupt();
        this.join();
        logger.info("Остановка процесса в порту");
    }

    public ReportPort getReport() {
        return new ReportPort(raidCapacity, raidService.getCount(), berthService.shipUnloadReports().size(), String.valueOf(endProcessPort - startProcessPort),
                berthService.getAllBerths().size());
    }

    @SneakyThrows
    @Override
    public void run() {
        processPort();
    }
}
