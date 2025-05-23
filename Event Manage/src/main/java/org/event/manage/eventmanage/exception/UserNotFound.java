package org.event.manage.eventmanage.exception;


import lombok.Setter;


@Setter
public class UserNotFound extends RuntimeException{

    private final String message;

    public UserNotFound(String message) {
        super(message);
        this.message = message;
    }
}