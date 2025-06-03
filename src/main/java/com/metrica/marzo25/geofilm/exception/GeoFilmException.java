package com.metrica.marzo25.geofilm.exception;

public class GeoFilmException extends RuntimeException {

	public GeoFilmException(String message) {
        super(message);
    }
    
    public GeoFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}