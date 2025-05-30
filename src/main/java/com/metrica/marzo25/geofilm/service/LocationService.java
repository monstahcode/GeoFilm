package com.metrica.marzo25.geofilm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.repository.LocationRepository;

@Service
public class LocationService {
	private final LocationRepository repository;

	public LocationService(LocationRepository repository) {
		this.repository = repository;
	}
	
	public Location saveLocation(Location location) {
		return repository.save(location);
	}
	
	public Optional<Location> getById(Long id){
		return repository.findById(id);
	}
	
	public List<Location> getAllWithoutThis(Long id){
		return repository.findByIdNot(id);
	}
	
	public List<Location> getAll(){
		return repository.findAll();
	}
	
}
