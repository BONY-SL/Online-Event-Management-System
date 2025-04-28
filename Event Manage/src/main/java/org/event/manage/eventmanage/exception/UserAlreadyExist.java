package org.event.manage.eventmanage.exception;
import lombok.Getter;


@Getter
public class UserAlreadyExist extends RuntimeException{

    private final String message;

    public UserAlreadyExist(String message) {
        super(message);
        this.message = message;
    }
}
