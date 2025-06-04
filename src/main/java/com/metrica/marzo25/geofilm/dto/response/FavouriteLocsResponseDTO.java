package com.metrica.marzo25.geofilm.dto.response;

import java.util.List;

public class FavouriteLocsResponseDTO {
    private String userEmail;
    private List<LocationDTO> favoriteLocations;

    public FavouriteLocsResponseDTO() {}

    public FavouriteLocsResponseDTO(String userEmail, List<LocationDTO> favoriteLocations) {
        this.userEmail = userEmail;
        this.favoriteLocations = favoriteLocations;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<LocationDTO> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(List<LocationDTO> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }
}
