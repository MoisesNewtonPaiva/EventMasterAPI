package com.eventMasterApi.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eventMasterApi.demo.DTO.EventDTO;
import com.eventMasterApi.demo.infrastructure.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@Controller
public class EventController {
    

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/events/insert")
    public ResponseEntity<EventDTO> insertEvent(@RequestBody EventDTO event) {
        EventDTO insertEvent = service.insertEvent(event);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(insertEvent.getId())
        .toUri();

        return ResponseEntity.created(uri).body(insertEvent);
    }

 
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> findAllEvents() {
        List<EventDTO> events = service.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> findEventById(@PathVariable Long id) {
        return service.findEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
