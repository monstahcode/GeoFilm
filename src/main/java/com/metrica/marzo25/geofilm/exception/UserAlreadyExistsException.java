package com.metrica.marzo25.geofilm.exception;

public class UserAlreadyExistsException extends GeoFilmException {

	private static final long serialVersionUID = -4621177106626890813L;

	public UserAlreadyExistsException(String message) {
        super(message);
    }
}