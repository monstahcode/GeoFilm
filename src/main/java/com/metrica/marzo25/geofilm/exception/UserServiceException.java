package com.metrica.marzo25.geofilm.exception;

public class UserServiceException extends GeoFilmException {
    public UserServiceException(String message) {
        super(message);
    }
    
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}