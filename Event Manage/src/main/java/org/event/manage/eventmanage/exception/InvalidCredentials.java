package org.event.manage.eventmanage.exception;

import lombok.Getter;

@Getter
public class InvalidCredentials extends RuntimeException{

    private final String message;

    public InvalidCredentials(String message) {
        super(message);
        this.message = message;
    }
}
