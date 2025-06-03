package com.metrica.marzo25.geofilm.exception;

public class LocationServiceException extends GeoFilmException {
    public LocationServiceException(String message) {
        super(message);
    }
    
    public LocationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}