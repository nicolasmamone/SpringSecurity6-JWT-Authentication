package com.nico.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    //endpoint protegido solo accedido despues de login
    @PostMapping(value = "demo")
    public String Welcome(){
        return "Welcome from secure endpoint";
    }
}
