package com.itheima.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AxiosController {
    @PostMapping("/api/a2")
    public String a2(){
        return "post request";
    }
}
