package com.metrica.marzo25.geofilm.controller;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;
import com.metrica.marzo25.geofilm.exception.ExternalApiException;
import com.metrica.marzo25.geofilm.service.SearchService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/media")
    public ResponseEntity<SearchResponseDTO> searchMedia(@RequestBody SearchRequestDTO request) throws ExternalApiException, IOException {
        return searchService.searchMedia(request);
    }
    
    @PostMapping("/preview")
    public ResponseEntity<SearchResponseDTO> searchPreview(@RequestBody SearchRequestDTO request) throws ExternalApiException, IOException {
    	return searchService.predictedSearchMedia(request);
    }
    
    @PostMapping("/id")
    public ResponseEntity<SearchResponseDTO> searchById(@RequestBody SearchRequestDTO request) throws IOException, InterruptedException {
    	return searchService.idSearchMedia(request);
    }
}