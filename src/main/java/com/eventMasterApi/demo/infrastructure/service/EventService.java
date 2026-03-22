package com.eventMasterApi.demo.infrastructure.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventMasterApi.demo.infrastructure.entities.Event;
import com.eventMasterApi.demo.infrastructure.repository2.EventRepository;
import com.eventMasterApi.demo.resources.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;


@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public Event insertEvent(Event event) {
        
        if (eventRepository.findByNameContainingIgnoreCase(event.getName()) != null) {
            throw new ResourceNotFoundException();
        } else {
            return eventRepository.save(event);
        }
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

}
