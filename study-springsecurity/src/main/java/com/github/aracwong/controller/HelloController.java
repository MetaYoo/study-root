package com.github.aracwong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "ok";
    }

    @PostMapping("/index")
    public String index() {
        return "index.html";
    }

    @PostMapping("/error")
    public String error() {
        return "error.html";
    }
}
