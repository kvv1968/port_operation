package com.example.port_operation.controllers;

import com.example.port_operation.model.Berth;
import com.example.port_operation.model.ReportPort;
import com.example.port_operation.model.RequestSetting;
import com.example.port_operation.model.Ship;
import com.example.port_operation.model.ShipUnload;
import com.example.port_operation.service.implemen.PortService;
import java.util.List;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    private final Log log = LogFactory.getLog(ViewController.class);
    private int raidCapacity;
    private int unloadingSpeed;
    private PortService portService;
    private int countThread = 0;

    public ViewController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping("report")
    public String getReport(Model model) {
        ReportPort report = portService.getReport();
        List<ShipUnload>shipUnloads = portService.shipUnloadReports();
        model.addAttribute("report", report);
        model.addAttribute("shipUnloads", shipUnloads);
        return "report";
    }

    @GetMapping("settings")
    public String getAdminPage() {
        return "settings";
    }

    @GetMapping("start")
    public String start(Model model) throws InterruptedException {
        return processController(model);
    }

    @GetMapping("stop")
    public String stop(Model model) throws InterruptedException {
        portService.stopProcessPort();
        model.addAttribute("msg", "Процесс остановлен");
        return "index";
    }

    @GetMapping("settings/param")
    public String getRequestSetting(RequestSetting request, Model model) {
        raidCapacity = request.getRaidCapacity();
        model.addAttribute("request", request);
        return "index";
    }
    @GetMapping("process")
    public String getProcess(Model model) {
        List<Ship>ships = portService.getAllShipsByRaid();
        List<Berth>berths = portService.getAllBerths();
        if (ships == null || berths == null){
            return "redirect:/";
        }
        model.addAttribute("ships", ships);
        model.addAttribute("berths", berths);
        return "process";
    }


    private String processController(Model model){
        if (raidCapacity == 0 && unloadingSpeed == 0) {
            model.addAttribute("msgg", "Перед запуском нужно настроить приложение");
            return "settings";
        }
        portService.setRaidCapacity(raidCapacity);
        String msg = "Процесс запущен...";
        model.addAttribute("msg", msg);
        portService.start();
        return "index";
    }

}
