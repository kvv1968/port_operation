package com.example.port_operation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class RequestSetting {
    private int raidCapacity;
    private int unloadingSpeed;
}
