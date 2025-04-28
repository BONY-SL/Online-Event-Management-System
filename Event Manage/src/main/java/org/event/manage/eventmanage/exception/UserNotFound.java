package org.event.manage.eventmanage.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFound extends RuntimeException{

    private final String message;

    public UserNotFound(String message) {
        this.message = message;
    }
}