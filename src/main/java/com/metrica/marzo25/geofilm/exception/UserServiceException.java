package com.metrica.marzo25.geofilm.exception;

public class UserServiceException extends GeoFilmException {

	private static final long serialVersionUID = 1523389941393501520L;

	public UserServiceException(String message) {
        super(message);
    }
    
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}