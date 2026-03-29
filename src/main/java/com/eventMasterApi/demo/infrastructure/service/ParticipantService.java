package com.eventMasterApi.demo.infrastructure.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventMasterApi.demo.DTO.ParticipantDTO;
import com.eventMasterApi.demo.infrastructure.entities.Participant;
import com.eventMasterApi.demo.infrastructure.repository2.ParticipantRepository;

import jakarta.transaction.Transactional;

@Service
public class ParticipantService {
    
    @Autowired
    private ParticipantRepository repository;

    ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    private ParticipantDTO convertToDTO(Participant participant) {
        return new ParticipantDTO(
            participant.getId(),
            participant.getName(),
            participant.getEmail(),
            participant.getActivities().stream().map(activity -> activity.getId()).toList(),
            participant.getEvents().stream().map(event -> event.getId()).toList()
        );
    }

    @Transactional
    public ResponseEntity<ParticipantDTO> insertParticipant(ParticipantDTO dto) {

        var existParticipant = repository.findByNameIgnoreCase(dto.name());
        if (!existParticipant.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Participant participant = new Participant();
            participant.setName(dto.name());
            participant.setEmail(dto.email());

            Participant savedParticipant = repository.save(participant);
            return ResponseEntity.ok(convertToDTO(savedParticipant));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    public ResponseEntity<List<ParticipantDTO>> findAllParticipant() {
        return ResponseEntity.ok(repository.findAll().stream().map(participant -> new ParticipantDTO(
            participant.getId(),
            participant.getName(),
            participant.getEmail(),
            participant.getActivities().stream().map(activity -> activity.getId()).toList(),
            participant.getEvents().stream().map(event -> event.getId()).toList()
        )).toList());
    }

    public Optional<ParticipantDTO> findParticipantById(Long id) {
        return repository.findById(id).map(participant -> new ParticipantDTO(
            participant.getId(),
            participant.getName(),
            participant.getEmail(),
            participant.getActivities().stream().map(activity -> activity.getId()).toList(),
            participant.getEvents().stream().map(event -> event.getId()).toList()
        ));
    }

    public ResponseEntity<ParticipantDTO> updateParticipant(Long id, ParticipantDTO dto) {
        Optional<Participant> existingParticipantOpt = repository.findById(id);

        if (existingParticipantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Participant existingParticipant = existingParticipantOpt.get();

        if (!existingParticipant.getName().equalsIgnoreCase(dto.name()) && repository.findByNameIgnoreCase(dto.name()).size() > 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            existingParticipant.setName(dto.name());
            existingParticipant.setEmail(dto.email());

            Participant updatedParticipant = repository.save(existingParticipant);
            return ResponseEntity.ok(convertToDTO(updatedParticipant));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Transactional
    public ResponseEntity<String> deleteParticipant(Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Participant with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting participant: " + e.getMessage());
        }
    }
}
