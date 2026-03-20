package com.eventMasterApi.demo.infrastructure.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventMasterApi.demo.infrastructure.entities.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    public List<Participant> findByNameContainingIgnoreCase(String name);
}