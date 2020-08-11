package com.github.aracwong.springboot.controller;

import com.github.aracwong.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private User user;

    @GetMapping(value = "/index")
    public String index() {
        return this.user.getId() + this.user.getName();
    }
}
