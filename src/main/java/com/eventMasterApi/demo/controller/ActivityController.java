package com.eventMasterApi.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eventMasterApi.demo.DTO.ActivityDTO;
import com.eventMasterApi.demo.infrastructure.service.ActivityService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    
    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping("/insert")   
    public ResponseEntity<ActivityDTO> insertActivity(@RequestBody ActivityDTO activity) {
        
        ActivityDTO insertActivity = service.insertActivity(activity).getBody();

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath() 
            .path("/api/activities/{id}")
            .buildAndExpand(insertActivity.id())
            .toUri();

        return ResponseEntity.created(uri).body(insertActivity);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ActivityDTO>> findAllActivities() {
        List<ActivityDTO> activities = service.findAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> findActivityById(@PathVariable Long id) {
        return service.findActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ActivityDTO> updateActivity (@PathVariable Long id, @RequestBody ActivityDTO activity) {
        return service.updateActivityDTO(id, activity);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivity (@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteActivity(id));
    }
}
