package com.metrica.marzo25.geofilm.exception;

public class GeoFilmException extends RuntimeException {

	private static final long serialVersionUID = -5559310833686935162L;

	public GeoFilmException(String message) {
        super(message);
    }
    
    public GeoFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}