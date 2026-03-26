package com.eventMasterApi.demo.DTO;

import java.util.List;

import com.eventMasterApi.demo.infrastructure.entities.Activity;

public record EventDTO(
    Long id,
    String name,
    String data,
    Double price,
    String description,
    List<Activity> activities,
    List<Long> participantIds
){}

