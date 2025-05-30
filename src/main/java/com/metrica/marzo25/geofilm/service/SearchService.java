package com.metrica.marzo25.geofilm.service;

import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;

@Service
public class SearchService {

    public String searchMovies(String query) {
        return "Resultados de búsqueda para: " + query;
    }

    public String searchActors(String query) {
        return "Resultados de búsqueda para actores: " + query;
    }

	public SearchResponseDTO searchMovies(SearchRequestDTO request) {
		return null;
	}
}