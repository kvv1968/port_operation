package com.example.port_operation.service.implemen;

import com.example.port_operation.configuration.RaidSpringEventPublisher;
import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.ReportPort;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class PortService {
    private final Log logger = LogFactory.getLog(PortService.class);
    private int raidCapacity;

    private RaidService raidService;
    private BerthService berthService;
    private Raid raid;
    private Berth[] berths;
    private long startProcessPort;
    private long endProcessPort;
    private boolean isRun = true;
    private ExecutorService executorService;
    @Autowired
    private RaidSpringEventPublisher raidEventPublisher;

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

    public void processPort(){
        logger.info("Запуск процесса в порту");
        startProcessPort = System.currentTimeMillis();
        raidService.deleteAllRepoShips();
        raid = new Raid(raidCapacity, true);
        raidService.setRaid(raid);
        berths = berthService.getBerths();
        raidService.setRunRaid(true);
        berthService.setBerthsRun(true);

        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(raidService);
        executorService.execute(berthService);
    }


    public void stopProcessPort()  {
        raidService.setRunRaid(false);
        berthService.setBerthsRun(false);
        logger.info("Устанавливаем вместимость рейда на 0");
        executorService.shutdown();
        logger.info(String.format("Остановка потока на рейде %s",raid));
        logger.info(String.format("Остановка потока на причалах %s",berths));
        endProcessPort = System.currentTimeMillis();
        logger.info("Остановка процесса в порту");
    }

    public ReportPort getReport() {
        return new ReportPort(raidCapacity, raidService.getCount(), berthService.shipUnloadReports().size(), String.valueOf(endProcessPort - startProcessPort),
                berthService.getAllBerths().size());
    }


}
