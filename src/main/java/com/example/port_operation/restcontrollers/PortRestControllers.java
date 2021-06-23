package com.example.port_operation.restcontrollers;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.implemen.PortService;
import java.util.List;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PortRestControllers {
    private final Log log = LogFactory.getLog(PortRestControllers.class);
    @Autowired
    private PortService portService;

    @GetMapping("/berths")
    public ResponseEntity<List<Berth>> getAllBerth() {
        List<Berth>berths = portService.getAllBerths();
        if (berths.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        log.info("Успешный запрос PortRestControllers  @GetMapping(/berths");
        return ResponseEntity.ok(berths);
    }

    @GetMapping("/raids")
    public ResponseEntity<List<Ship>> getAllShipByRaid() {
        List<Ship>shipList = portService.getAllShipsByRaid();
        if (shipList == null){
            return ResponseEntity.noContent().build();
        }
        log.info("Успешный запрос PortRestControllers  @GetMapping(/raids)");
        return ResponseEntity.ok(shipList);
    }


}
