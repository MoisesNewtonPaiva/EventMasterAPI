package com.eventMasterApi.demo.infrastructure.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventMasterApi.demo.DTO.EventDTO;
import com.eventMasterApi.demo.infrastructure.entities.Event;
import com.eventMasterApi.demo.infrastructure.repository2.EventRepository;
import com.eventMasterApi.demo.resources.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;


@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;


    @Transactional
    public EventDTO insertEvent(EventDTO dto) {
        
        if (eventRepository.findByNameContainingIgnoreCase(dto.name()) != null) {
            throw new ResourceNotFoundException();
        } else {
            Event event = new Event();
            event.setName(dto.name());
            event.setData(dto.data());
            event.setPrice(dto.price());
            event.setDescription(dto.description());

            Event savedEvent = eventRepository.save(event);

            return new EventDTO(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getData(),
                savedEvent.getPrice(),
                savedEvent.getDescription()
            );
        }
    }

    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream().map(event -> new EventDTO(
            event.getId(),
            event.getName(),
            event.getData(),
            event.getPrice(),
            event.getDescription()
        )).toList();
    }

    public Optional<EventDTO> findEventById(Long id) {
        return eventRepository.findById(id).map(event -> new EventDTO(
            event.getId(),
            event.getName(),
            event.getData(),
            event.getPrice(),
            event.getDescription()
        ));
    }

}
