package com.eventMasterApi.demo.infrastructure.service;


import com.eventMasterApi.demo.infrastructure.repository2.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventMasterApi.demo.DTO.EventDTO;
import com.eventMasterApi.demo.infrastructure.entities.Event;
import com.eventMasterApi.demo.infrastructure.repository2.EventRepository;
import com.eventMasterApi.demo.resources.exceptions.DataBaseException;
import com.eventMasterApi.demo.resources.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;


@Service
public class EventService {
    
    private final ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;


    EventService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    private EventDTO convertToDTO(Event event) {
        return new EventDTO(
            event.getId(),
            event.getName(),
            event.getData(),
            event.getPrice(),
            event.getDescription(),
            event.getActivities(),
            event.getParticipants().stream().map(participant -> participant.getId()).toList()
        );
    }


    @Transactional
    public EventDTO insertEvent(EventDTO dto) {
        // Validação antes de operação no banco
        var existingEvent = eventRepository.findByNameIgnoreCase(dto.name());
    
        if (!existingEvent.isEmpty()) {
            throw new DataBaseException("An event with the name '" + dto.name() + "' already exists.");
        }

        try {
            Event event = new Event();
            event.setName(dto.name());
            event.setData(dto.data());
            event.setPrice(dto.price());
            event.setDescription(dto.description());

            event.setActivities(new ArrayList<>()); 
            event.setParticipants(new ArrayList<>());

            Event savedEvent = eventRepository.save(event);
            return convertToDTO(savedEvent);
        } catch (Exception e) {
            throw new DataBaseException("Error saving event: " + e.getMessage());
        }
    }

    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream().map(event -> new EventDTO(
            event.getId(),
            event.getName(),
            event.getData(),
            event.getPrice(),
            event.getDescription(),
            event.getActivities(),
            event.getParticipants().stream().map(participant -> participant.getId()).toList()
        )).toList();
    }

    public Optional<EventDTO> findEventById(Long id) {
        return eventRepository.findById(id).map(event -> new EventDTO(
            event.getId(),
            event.getName(),
            event.getData(),
            event.getPrice(),
            event.getDescription(),
            event.getActivities(),
            event.getParticipants().stream().map(participant -> participant.getId()).toList()
        ));
    }

    public ResponseEntity<EventDTO> updateEventDTO(Long id, EventDTO dto) {
        try {
            Event event = eventRepository.findById(id)
                .orElseThrow (() -> new ResourceNotFoundException());
            
            event.setName(dto.name());
            event.setData(dto.data());
            event.setPrice(dto.price());
            event.setDescription(dto.description());

            EventDTO updatedDTO = convertToDTO(eventRepository.save(event));

            return ResponseEntity.ok(updatedDTO);

        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }
    
    @Transactional
    public String deleteEventById(Long id) {
        try {
            if (!eventRepository.existsById(id)) {
                throw new ResourceNotFoundException();
            }
            eventRepository.deleteById(id);
            
            return "Event with ID " + id + " was deleted successfully.";
        } catch (DataBaseException e) {
            throw e;
        }
    }

}
