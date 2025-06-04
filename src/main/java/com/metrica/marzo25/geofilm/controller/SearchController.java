package com.metrica.marzo25.geofilm.controller;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;
import com.metrica.marzo25.geofilm.exception.ExternalApiException;
import com.metrica.marzo25.geofilm.service.SearchService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService searchService) {
        this.service = searchService;
    }

    @PostMapping("/name")
    public ResponseEntity<SearchResponseDTO> searchMedia(@RequestBody SearchRequestDTO request) throws ExternalApiException, IOException {
        return service.searchMediaByName(request);
    }
    
    @PostMapping("/predict/name")
    public ResponseEntity<SearchResponseDTO> searchPreview(@RequestBody SearchRequestDTO request) throws ExternalApiException, IOException {
    	return service.predictedSearchMedia(request);
    }
    
    @PostMapping("/id")
    public ResponseEntity<SearchResponseDTO> searchById(@RequestBody SearchRequestDTO request) throws IOException, InterruptedException {
    	return service.searchMediaById(request);
    }
}