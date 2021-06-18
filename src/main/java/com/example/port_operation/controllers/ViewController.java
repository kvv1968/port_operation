package com.example.port_operation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/report")
    public String getReport() {
        return "report";
    }

    @GetMapping("/settings")
    public String getAdminPage() {
        return "settings";
    }
}
