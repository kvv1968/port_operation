package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Ship;
import java.util.List;

public interface ShipService {
    List<Ship> getAllShipsRaid ();
    boolean addShipRepo();
    void deleteShip (Ship ship);
}
