package com.rentevent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class DemoAdminController {
    @PostMapping(value = "demo")
    public String welcome() {
        return "Welcome Admin, from secure endpoint";
    }
}
