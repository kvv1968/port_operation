package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.ReportPort;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.BerthService;
import com.example.port_operation.service.interfaces.RaidService;
import java.util.List;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
@Data
public class PortService {
    private final Log logger = LogFactory.getLog(PortService.class);
    private int raidCapacity;
    private int unloadingSpeed;

    private RaidService raidService;
    private BerthService berthService;
    private Raid raid;
    private Thread raidThread, berthTthread;
    private long start;
    private long end;

    public PortService(RaidService raidService, BerthService berthService) {
        this.raidService = raidService;
        this.berthService = berthService;
    }

    public List<Ship> getAllShipsByRaid() {
        return raidService.getShips();
    }


    public List<Berth> getAllBerths() {
        return berthService.getAllBerths();
    }

    public List<Ship> shipsReports(){
        return berthService.shipsReports();
    }



    public void processPort() {
        start = System.currentTimeMillis();
        raidService.deleteAllRepoShips();
        raid = raidService.getRaidCaparasity(raidCapacity);
        raidThread = new Thread(raidService, "Поток RaidService");
        raidThread.setPriority(10);
        raidThread.start();
        berthService.setUnloadingSpeed(unloadingSpeed);
        berthTthread = new Thread(berthService, "Поток BerthService");
        berthTthread.start();
        logger.info(String.format("Потоки %s и %s в сервисах запущены", raidThread, berthTthread));
    }

    public void stopThread() throws InterruptedException {
        berthService.stopBehthsThreads();
        raidThread.interrupt();
        berthTthread.interrupt();
        raidThread.join();
        berthTthread.join();
        end = System.currentTimeMillis();
        logger.info(String.format("Потоки %s и %s в сервисах остановлены", raidThread, berthTthread));
    }

    public ReportPort getReport() {
        return new ReportPort(raidCapacity, unloadingSpeed,
                raidService.getCount(), berthService.shipsReports().size(), String.valueOf(end - start),
                berthService.getAllBerths().size());
    }
}
