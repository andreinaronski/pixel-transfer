package com.pixel.transfer.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(Long id) {
        throw new UserNotFoundException(String.format(String.format("User with id = %s not found", id)));
    }
}
