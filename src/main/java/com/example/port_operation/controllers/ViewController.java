package com.example.port_operation.controllers;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.Raid;
import com.example.port_operation.model.RequestSetting;
import com.example.port_operation.model.Ship;
import com.example.port_operation.service.interfaces.PortService;
import com.example.port_operation.service.interfaces.RaidService;
import com.example.port_operation.service.interfaces.ShipService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    private int raidCapacity;
    private int unloadingSpeed;
    private String[] typeCargos;
    private boolean isRun = true;
    private List<Berth> berths ;
    private List<Ship>shipList;

    private PortService portService;
    private ShipService shipService;
    private RaidService raidService;
    private Raid raid;

    public ViewController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping("report")
    public String getReport() {
        return "report";
    }

    @GetMapping("settings")
    public String getAdminPage() {
        return "settings";
    }

    @GetMapping("start")
    public String start(Model model){
        if (typeCargos == null && raidCapacity == 0 && unloadingSpeed == 0){
            model.addAttribute("msg", "Перед запуском нужно настроить приложение");
            return "settings";
        }
        portService.addTypeCargo(typeCargos);//записали тип груза в базу
        berths = portService.getBerthsRepoTypeCargo();//сформировали по одному причалу согласно типам груза
        shipService = portService.getShipService();
        raidService = portService.getRaidService();
        raid = portService.getRaid(raidCapacity);//сформировали рейд с полученным объемом
        while (isRun){
            shipService.addShipRepo(raidCapacity);//сгенерировали корабли и записали в базу
            shipList = Arrays.stream(raid.getShips()).collect(Collectors.toList());//заполняем рейд кораблями
            for (Ship ship:shipList){
                portService.unloadingShipByBerth(ship, unloadingSpeed);//выгружаем корабли на причале
            }
        }
        return "redirect:/";

    }
    @GetMapping("stop")
    public String stop(){
        isRun = false;
        return "redirect:/";
    }
    @GetMapping("settings/param")
    public String getRequestSetting(RequestSetting request, Model model){
        raidCapacity = request.getRaidCapacity();
        unloadingSpeed = request.getUnloadingSpeed();
        typeCargos = request.getTypeCargos();
        model.addAttribute("request", request);
        return "index";
    }
    @GetMapping("index")
    public String index(){
        return "index";
    }
}
