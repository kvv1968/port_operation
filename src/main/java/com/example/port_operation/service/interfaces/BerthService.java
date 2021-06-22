package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import java.util.List;

public interface BerthService {
    void unloadingBerth(int unloadingSpeed);
    List<Berth> getAllBerths();
    List<Ship> shipsRaid();
}
