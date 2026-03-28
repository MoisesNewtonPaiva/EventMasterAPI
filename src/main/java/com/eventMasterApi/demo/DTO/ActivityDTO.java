package com.eventMasterApi.demo.DTO;

import java.time.Instant;
import java.util.List;

import com.eventMasterApi.demo.infrastructure.entities.Event;

public record ActivityDTO (
    Long id,
    String title,
    String description,
    Instant date,
    List<Long> participantIds,
    Event event
) {}
