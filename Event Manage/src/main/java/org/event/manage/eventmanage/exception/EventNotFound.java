package org.event.manage.eventmanage.exception;

import lombok.Getter;

@Getter
public class EventNotFound extends RuntimeException{

    private final String message;

    public EventNotFound(String message) {
        super(message);
        this.message = message;
    }
}
