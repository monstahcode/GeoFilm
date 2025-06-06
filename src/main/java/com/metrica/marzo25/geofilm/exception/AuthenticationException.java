package com.metrica.marzo25.geofilm.exception;

public class AuthenticationException extends GeoFilmException {

	private static final long serialVersionUID = -259691059496849119L;

	public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}