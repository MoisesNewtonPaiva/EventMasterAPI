package com.eventMasterApi.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventMasterApi.demo.infrastructure.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    
    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }
}
