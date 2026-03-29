package com.eventMasterApi.demo.DTO;

import java.util.List;

public record ParticipantDTO(
    Long id,
    String name,
    String email,
    List<Long> activityIds,
    List<Long> eventIds
) {}
