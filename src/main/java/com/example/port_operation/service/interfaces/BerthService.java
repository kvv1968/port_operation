package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.context.ApplicationListener;

public interface BerthService extends ApplicationListener<Berth> {
    List<Berth> getAllBerths();
    List<ShipUnload> shipUnloadReports();
    Berth[] getBerths();
    void processBerth(Berth berth, List<Ship> shipsRaid) throws ExecutionException, InterruptedException;

    void processBerth(Ship ship) throws ExecutionException, InterruptedException;
}
