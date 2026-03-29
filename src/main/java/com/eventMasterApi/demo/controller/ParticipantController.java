package com.eventMasterApi.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eventMasterApi.demo.DTO.ParticipantDTO;
import com.eventMasterApi.demo.infrastructure.service.ParticipantService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public class ParticipantController {
    
    @Autowired
    private ParticipantService service;

    ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ParticipantDTO>> findAllParticipant() {
        List<ParticipantDTO> participants = service.findAllParticipant().getBody();
        return ResponseEntity.ok(participants);
    }
    
    @PostMapping("/insert")
    public ResponseEntity<ParticipantDTO> insertParticipant(@RequestBody ParticipantDTO participant) {

        ParticipantDTO insertParticipant = service.insertParticipant(participant).getBody();

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath() 
            .path("/api/participants/{id}")
            .buildAndExpand(insertParticipant.id())
            .toUri();

        return ResponseEntity.created(uri).body(insertParticipant);
    }

    @GetMapping("/getId/{id}")
    public ResponseEntity<ParticipantDTO> findParticipantById(@PathVariable Long id) {
        return service.findParticipantById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant(@PathVariable Long id, @RequestBody ParticipantDTO participant) {
        return service.updateParticipant(id, participant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id) {
        return service.deleteParticipant(id);
    }
}
