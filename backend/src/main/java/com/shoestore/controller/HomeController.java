package com.shoestore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home(@RequestParam(value = "error", required = false) String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Shoe Store API is running");
        if (error != null) {
            response.put("error", error);
        }
        return response;
    }
}
