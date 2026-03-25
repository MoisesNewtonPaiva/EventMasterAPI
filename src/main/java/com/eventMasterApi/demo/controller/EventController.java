package com.eventMasterApi.demo.controller;

import com.eventMasterApi.demo.EventMasterApplication;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class EventController {
    

    private final EventMasterApplication eventMasterApplication;
    private final EventService service;

    public EventController(EventService service, EventMasterApplication eventMasterApplication) {
        this.service = service;
        this.eventMasterApplication = eventMasterApplication;
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

    @GetMapping("/eventsget/{id}")
    public ResponseEntity<EventDTO> findEventById(@PathVariable Long id) {
        return service.findEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO event) {
            return service.updateEventDTO(id, event);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteEventById(id));
    }
}
