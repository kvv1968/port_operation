package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.ShipUnload;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.context.ApplicationListener;

public interface BerthService extends ApplicationListener<Berth> {
    List<Berth> getAllBerths();
    List<ShipUnload> shipUnloadReports();
    void setUnloadingSpeed(int unloadingSpeed);
    void setBerths(Berth[] berths);
    void processBerth(Berth berth, Raid raid) throws ExecutionException, InterruptedException;
}
