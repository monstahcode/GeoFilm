package com.metrica.marzo25.geofilm.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.metrica.marzo25.geofilm.extra.Media;

public class SearchResponseDTO {

	private List<Media> mediaList;
    private String message;
    private boolean success;

    public SearchResponseDTO(String message) {
        this.message = message;
        this.success = false;
    }
    
    public SearchResponseDTO(List<Media> mediaList) {
        this.message = "Encontrados " + mediaList.size();
        this.success = true;
        this.mediaList = new ArrayList<>();
        mediaList.forEach(media -> this.mediaList.add(media));
    }

    public boolean isSuccess() {
    	return success;
    }

    public String getErrorMessage() {
        return message;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
