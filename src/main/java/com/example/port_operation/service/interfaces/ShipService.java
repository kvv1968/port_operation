package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Ship;
import java.util.List;

public interface ShipService {
    List<Ship> getAllShipsRaid ();
    void addShipRepo(int caparasity);
    void deleteShip (Ship ship);
}
