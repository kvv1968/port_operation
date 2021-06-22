package com.example.port_operation.controllers;

import com.example.port_operation.model.RequestSetting;
import com.example.port_operation.service.implemen.PortService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    private int raidCapacity;
    private int unloadingSpeed;
    private Thread thread;
    private PortService portService;

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
        if (raidCapacity == 0 && unloadingSpeed == 0){
            model.addAttribute("msg", "Перед запуском нужно настроить приложение");
            return "settings";
        }
        portService.setRaidCapacity(raidCapacity);
        portService.setUnloadingSpeed(unloadingSpeed);
        thread = new Thread(portService,"Процесс запущен");
        thread.start();
        String msg = "Процесс запущен...";
        model.addAttribute("msg-index", msg);
        return "index";
    }
    @GetMapping("stop")
    public String stop(Model model) throws InterruptedException {
       thread.interrupt();
       thread.join();
        model.addAttribute("msg-index", "Процесс остановлен");
        return "redirect:/";
    }
    @GetMapping("settings/param")
    public String getRequestSetting(RequestSetting request, Model model){
        raidCapacity = request.getRaidCapacity();
        unloadingSpeed = request.getUnloadingSpeed();
        model.addAttribute("request", request);
        return "index";
    }
    @GetMapping("index")
    public String index(){
        return "index";
    }
}
