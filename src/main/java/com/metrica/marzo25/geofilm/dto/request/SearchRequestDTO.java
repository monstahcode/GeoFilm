package com.metrica.marzo25.geofilm.dto.request;

public class SearchRequestDTO {
    private String mediaName;

    public SearchRequestDTO() {
    }

    public SearchRequestDTO(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaName() {
        return mediaName;
    }
}
