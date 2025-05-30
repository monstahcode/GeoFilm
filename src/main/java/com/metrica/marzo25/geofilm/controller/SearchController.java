package com.metrica.marzo25.geofilm.controller;

import com.metrica.marzo25.geofilm.dto.request.SearchRequest;
import com.metrica.marzo25.geofilm.dto.response.SearchResponse;
import com.metrica.marzo25.geofilm.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/movies")
    public ResponseEntity<SearchResponse> searchMovies(@RequestBody SearchRequest request) {
        try {
            SearchResponse response = searchService.searchMovies(request);
            if (response.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            SearchResponse errorResponse = new SearchResponse(false, "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}