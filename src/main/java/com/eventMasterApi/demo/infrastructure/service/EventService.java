package com.eventMasterApi.demo.infrastructure.service;


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
        }
        return eventRepository.save(event);
    }

}
