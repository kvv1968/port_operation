package com.example.port_operation.service.implemen;

import com.example.port_operation.model.Raid;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidServiceImpl implements RaidService {

    private ShipService shipService;

    private Raid raid;

    private List<Ship> shipsList;

    @Autowired
    public RaidServiceImpl(ShipService shipService) {
        this.shipService = shipService;
    }


    @Override
    public void addShipByRaid() {
        int i = 0;
        int capacity = raid.getRaidCapacity();
        if (raid.isFreeRaid()) {
            List<Ship>shipsRepo = shipService.getAllShipsRepo().stream()
                    .filter(ship -> ship.getAmountCargo() != 0)
                    .collect(Collectors.toList());
            shipsList = getShips();
            int sizeShipsList = shipsList.size();
            while (capacity > sizeShipsList){
                if (shipsRepo.isEmpty() && shipsList.isEmpty()){
                    shipsList.add(shipService.shipGeneration());
                    capacity--;
                } else if(shipsList.isEmpty()){
                    shipsList.add(shipsRepo.get(i++));
                    capacity--;
                }else if (shipsRepo.isEmpty()){
                    shipsList.add(shipService.shipGeneration());
                    capacity--;
                } else {
                    shipsList.add(shipsRepo.get(i++));
                    capacity--;
                }
            }
            setShipsList(shipsList);
        }
    }

    @Override
    public Raid getRaidCaparasity(int caparasity) {
        raid = Raid.getInstance(caparasity);
        return raid;
    }

    @Override
    public void deleteShipByRaid(Ship ship) {
        raid.removeRaid(ship);
    }

    public List<Ship> getShips() {
        shipsList = raid.getShips();
        return shipsList;
    }

    @Override
    public void updateAllShips(List<Ship> ships) {
        ships.forEach(ship -> shipService.updateShip(ship));
    }

    @Override
    public Raid getRaid() {
        return raid;
    }

    public void setShipsList(List<Ship> shipsList) {
        raid.setShips(shipsList);
    }
}
