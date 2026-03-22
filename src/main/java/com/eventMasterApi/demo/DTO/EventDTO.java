package com.eventMasterApi.demo.DTO;

import java.util.Map;

public record EventDTO(
    Long id,
    String name,
    String data,
    Double price,
    String description
) {

    public Map<String, ?> getId() {
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }}
