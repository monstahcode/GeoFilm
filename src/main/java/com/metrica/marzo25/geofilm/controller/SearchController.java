package com.metrica.marzo25.geofilm.controller;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;
import com.metrica.marzo25.geofilm.service.SearchService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/movies")
    public SearchResponseDTO searchMedia(@RequestBody SearchRequestDTO request) {
        return searchService.searchMedia(request);
    }
}