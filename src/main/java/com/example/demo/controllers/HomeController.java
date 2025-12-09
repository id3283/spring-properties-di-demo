package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // this method will respond to http://localhost:8080/howdy
    @RequestMapping(path = "/howdy", method = RequestMethod.GET)
    public String index() {
        return "Howdy World!";
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(name = "greetName", defaultValue = "Dave") String name) {
        return "Hello " + name + "!";
    }
}