package com.metrica.marzo25.geofilm.service;

import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.dto.request.SearchRequest;
import com.metrica.marzo25.geofilm.dto.response.SearchResponse;

@Service
public class SearchService {

    public String searchMovies(String query) {
        return "Resultados de búsqueda para: " + query;
    }

    public String searchActors(String query) {
        return "Resultados de búsqueda para actores: " + query;
    }

	public SearchResponse searchMovies(SearchRequest request) {
		return null;
	}
}