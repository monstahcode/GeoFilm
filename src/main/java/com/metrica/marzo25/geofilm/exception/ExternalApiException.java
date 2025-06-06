package com.metrica.marzo25.geofilm.exception;

public class ExternalApiException extends GeoFilmException {

	private static final long serialVersionUID = -3086059524445180300L;

	public ExternalApiException(String message) {
        super(message);
    }
    
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}