package com.metrica.marzo25.geofilm.dto.request;

public class SearchRequest {
    private String query;
    private String type;

    public SearchRequest() {
    }

    public SearchRequest(String query, String type) {
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
