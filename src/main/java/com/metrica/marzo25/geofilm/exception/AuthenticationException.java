package com.metrica.marzo25.geofilm.exception;

public class AuthenticationException extends GeoFilmException {
    public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}