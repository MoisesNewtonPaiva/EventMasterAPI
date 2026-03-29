package com.eventMasterApi.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.eventMasterApi.demo.DTO.EventDTO;
import com.eventMasterApi.demo.infrastructure.service.EventService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;




@RestController
@RequestMapping("/api/events")
public class EventController {
    

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventDTO> insertEvent(@RequestBody EventDTO event) {
        EventDTO insertEvent = service.insertEvent(event).getBody();

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath() 
            .path("/api/events/{id}")
            .buildAndExpand(insertEvent.id())
            .toUri();
            
        return ResponseEntity.created(uri).body(insertEvent);
    }

 
    @GetMapping
    public ResponseEntity<List<EventDTO>> findAllEvents() {
        List<EventDTO> events = service.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> findEventById(@PathVariable Long id) {
        return service.findEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO event) {
            return service.updateEventDTO(id, event);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteEventById(id));
    }
}
