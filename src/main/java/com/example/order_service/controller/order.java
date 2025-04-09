package com.example.order_service.controller;

import com.example.order_service.models.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class order {


    @GetMapping("v1/order-service")
    public Version orderServiceV1() {
        return new Version("v1","order-service");
    }

    @GetMapping("v2/order-service")
    public Version orderServiceV2() {
        return new Version("v2","order-service");
    }

}
