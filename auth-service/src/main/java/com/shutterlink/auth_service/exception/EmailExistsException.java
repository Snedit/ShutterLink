package com.shutterlink.auth_service.exception;


public class EmailExistsException extends RuntimeException {
    EmailExistsException(String message)
    {
        super(message);
    }
}
