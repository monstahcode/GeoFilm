package com.metrica.marzo25.geofilm.dto.response;

import java.util.List;

import com.metrica.marzo25.geofilm.extra.Media;

public class SearchResponseDTO {

	private Media[] mediaList;
    private String message;
    private boolean success;

    public SearchResponseDTO(String message) {
        this.message = message;
        this.success = false;
    }
    
    public SearchResponseDTO(List<Media> mediaList) {
        this.message = "Encontrados " + mediaList.size();
        this.success = true;
        this.mediaList = mediaList.stream()
        		.toArray(Media[]::new);
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

	public Media[] getMediaList() {
		return mediaList;
	}

	public void setMediaList(Media[] mediaList) {
		this.mediaList = mediaList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
    
}
