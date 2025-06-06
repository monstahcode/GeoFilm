package com.metrica.marzo25.geofilm.dto.request;

import com.metrica.marzo25.geofilm.dto.response.LocationDTO;

public class FavouriteLocsRequestDTO {
    
    private String email;
    private LocationDTO locationData;
    
    public FavouriteLocsRequestDTO() {}
    
    public FavouriteLocsRequestDTO(String email, LocationDTO locationData) {
        this.email = email;
        this.locationData = locationData;
    }
    
    public FavouriteLocsRequestDTO(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocationDTO getLocationData() {
		return locationData;
	}

	public void setLocationData(LocationDTO locationData) {
		this.locationData = locationData;
	}
}