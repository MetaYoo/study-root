package com.github.aracwong.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/hello")
    public String hello() {
        return "ok";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }
}
