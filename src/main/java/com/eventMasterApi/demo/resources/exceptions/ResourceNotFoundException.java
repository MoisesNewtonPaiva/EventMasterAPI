package com.eventMasterApi.demo.resources.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException() {
        super("Resource not found.");
    }
    
}
