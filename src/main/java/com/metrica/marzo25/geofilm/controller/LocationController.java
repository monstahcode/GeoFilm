package com.metrica.marzo25.geofilm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.service.LocationService;

@RestController
@RequestMapping("/api/v1/location")
@CrossOrigin(origins = "*")
public class LocationController {

	private final LocationService service;

	public LocationController(LocationService service) {
		this.service = service;
	}
	
	@PostMapping
	public Location saveLocation(Location location) {
		return service.saveLocation(location);
	}
	
	@GetMapping("/id")
	public Optional<Location> getById(Long id){
		return service.getById(id);
	}
	
	@GetMapping("/all")
	public List<Location> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/all/without")
	public List<Location> getAllWithoutThis(Long id){
		return service.getAllWithoutThis(id);
	}
	
}
