package com.metrica.marzo25.geofilm.exception;

public class LocationServiceException extends GeoFilmException {

	private static final long serialVersionUID = -7530914550431358988L;

	public LocationServiceException(String message) {
        super(message);
    }
    
    public LocationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}