package com.eventMasterApi.demo.resources.exceptions;

public class DataBaseException extends RuntimeException {
    
    public DataBaseException(String message) {
        super("Do not delete this event because it has activities associated with it.");
    }

    public DataBaseException(String message, Throwable cause) {
        super("Do not delete this event because it has activities associated with it.", cause);
    }
}
