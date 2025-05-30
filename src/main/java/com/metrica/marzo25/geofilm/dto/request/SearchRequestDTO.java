package com.metrica.marzo25.geofilm.dto.request;

public class SearchRequestDTO {
    private String query;
    private String type;

    public SearchRequestDTO() {
    }

    public SearchRequestDTO(String query, String type) {
        this.query = query;
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
