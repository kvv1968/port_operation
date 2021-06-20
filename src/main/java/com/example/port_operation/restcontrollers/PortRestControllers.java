package com.example.port_operation.restcontrollers;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.PortService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PortRestControllers<T> {

    @Autowired
    private PortService portService;

    @GetMapping("/berths")
    public List<Berth> getAllBerth(){
        return portService.getBerths();
    }

    @GetMapping("/raids")
    public List<Ship> getAllShipByRaid(){
        return Arrays.stream(portService.getRaidService().getRaid().getShips()).collect(Collectors.toList());
    }


}
