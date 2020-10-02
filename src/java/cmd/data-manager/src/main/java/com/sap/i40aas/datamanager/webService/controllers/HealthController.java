package com.sap.i40aas.datamanager.webService.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @RequestMapping("/health")
    public String getServerHealth() {

        return "Server UP!";
    }


}
