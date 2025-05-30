package com.metrica.marzo25.geofilm.extra;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
@CrossOrigin(origins = "*")
public class SearcherController {

	@GetMapping("/name/{name}")
    public List<Media> searchMediaByName(@PathVariable String name) throws IOException {
        return Searcher.searchMediaByName(name);
    }
	
	@GetMapping("/id/{id}")
	public Media searchMediaById(@PathVariable String id) throws IOException{
		return Searcher.searchMediaById(id);
	}
}
