package com.metrica.marzo25.geofilm.exception;

public class ExternalApiException extends GeoFilmException {
    public ExternalApiException(String message) {
        super(message);
    }
    
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}