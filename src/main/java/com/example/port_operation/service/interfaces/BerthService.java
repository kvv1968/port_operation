package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import java.util.List;

public interface BerthService extends Runnable{
    List<Berth> getAllBerths();
    void stopBehthsThreads() throws InterruptedException;
    List<Ship> shipsReports();
    void setUnloadingSpeed(int unloadingSpeed);
}
