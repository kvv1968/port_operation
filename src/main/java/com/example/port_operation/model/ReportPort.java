package com.example.port_operation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportPort {
    private int raidCapacity;
    private int numberShipsEnteredRaid;
    private int numberUnloadedShips;
    private String portOpeningHours;
    private int numberBerths;

    public String getPortOpeningHours() {
        long millis = Long.parseLong(portOpeningHours);
        long minutes = (millis / 1000)  / 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%d минут, %d секунд", minutes, seconds);
    }
}
