package com.metrica.marzo25.geofilm.exception;

public class MediaSearchException extends GeoFilmException {

	private static final long serialVersionUID = 3482765904045758131L;

	public MediaSearchException(String message) {
        super(message);
    }
    
    public MediaSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}