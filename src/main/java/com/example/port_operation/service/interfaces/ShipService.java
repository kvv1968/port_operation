package com.example.port_operation.service.interfaces;

import com.example.port_operation.model.Ship;
import java.util.List;

public interface ShipService {
    List<Ship> getAllShipsRepo ();
    void deleteShipRepo (Ship ship);
    Ship updateShip(Ship ship);
    Ship  shipGeneration();

}
