package com.metrica.marzo25.geofilm.extra;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearcherController {

	@GetMapping
    public List<Media> search(@RequestParam String query) throws IOException {
        return Searcher.searchIdsWithName(query);
    }
	
	@GetMapping("/id/{id}")
    public List<Media> searchById(@PathVariable String id) throws IOException {
        return Searcher.searchIdsWithName(id);
    }
}
