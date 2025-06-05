package com.metrica.marzo25.geofilm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.dto.request.FavouriteLocsRequestDTO;
import com.metrica.marzo25.geofilm.dto.request.UserFavouriteLocsRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.FavouriteLocsResponseDTO;
import com.metrica.marzo25.geofilm.dto.response.LocationDTO;
import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.exception.UserNotFoundException;
import com.metrica.marzo25.geofilm.repository.LocationRepository;
import com.metrica.marzo25.geofilm.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final LocationRepository locationRepository;
    
    @Autowired
    public UserService(UserRepository repository, LocationRepository locationRepository) {
        this.repository = repository;
        this.locationRepository = locationRepository;
    }

    public Optional<User> getByEmail(String email) {
    	if (email == null || email.isBlank()) {
    		throw new IllegalArgumentException("El email no puede estar vacío");
    	}
    	return repository.findByEmail(email);
    }
    
    public User getByEmailOrThrow(String email) {
        return getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con email: " + email));
    }
    
    public User saveUser(User user) {
    	if (user == null) {
    		throw new IllegalArgumentException("El usuario no puede ser nulo");
    	}
    	return repository.save(user);
    }
    
    public Optional<User> getById(Long id) {
    	if (id == null) {
    		throw new IllegalArgumentException("El ID no puede ser nulo");
    	}
    	return repository.findById(id);
    }
    
    public User getByIdOrThrow(Long id) {
        return getById(id)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con ID: " + id));
    }
    
    public ResponseEntity<FavouriteLocsResponseDTO> getFavouriteLocs(UserFavouriteLocsRequestDTO request) {
    	User user = getByEmailOrThrow(request.getUserEmail());
    	List<LocationDTO> favouriteLocations = user.getFavoriteLocations().stream().map(favLoc -> new LocationDTO(
    			favLoc.getAddress(),
    			favLoc.getFictionalAddress(), favLoc.getLatitude(),
    			favLoc.getLongitude()
		)).toList();
    	
    	return ResponseEntity.ok(
    			new FavouriteLocsResponseDTO(
						user.getEmail(),
						favouriteLocations
				)
		);
    }
    
    public ResponseEntity<FavouriteLocsResponseDTO> addFavouriteLocs(FavouriteLocsRequestDTO request) {
    	if(request.getLocationData().getLatitude() == null || request.getLocationData().getLongitude() == null) {
    		throw new IllegalArgumentException("Coordenadas no válidas");
    	}

    	User user = getByEmailOrThrow(request.getEmail());
   
    	Optional<Location> locationOpt = locationRepository.findAll().stream()
    			.filter(loc -> loc.getLatitude().equals(request.getLocationData().getLatitude()) && loc.getLongitude().equals(request.getLocationData().getLongitude()))
    			.findFirst();
    	
    	Location location;
    	if (!locationOpt.isPresent()) {
    		location = new Location(request.getLocationData());
    	} else {
    		location = locationOpt.get();
    	}
   		user.addFavoriteLocation(location);

    	repository.save(user);

    	return ResponseEntity.status(HttpStatus.ACCEPTED).body(
    			new FavouriteLocsResponseDTO(user.getEmail(), List.of(new LocationDTO(
    					location.getAddress(),
    					location.getFictionalAddress(),
    					location.getLatitude(),
    					location.getLongitude()
   					)
    			)
  			)
    	);
    }

    public ResponseEntity<FavouriteLocsResponseDTO> removeFavouriteLocs(FavouriteLocsRequestDTO request) {
    	if(request.getLocationData().getLatitude() == null || request.getLocationData().getLongitude() == null) {
    		throw new IllegalArgumentException("Coordenadas no válidas");
    	}

    	User user = getByEmailOrThrow(request.getEmail());
    	Location location = user.getFavoriteLocations().stream()
    			.filter(loc -> loc.getLatitude().equals(request.getLocationData().getLatitude()) && loc.getLongitude().equals(request.getLocationData().getLongitude()))
    			.findFirst()
    			.orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada en los favoritos del usuario"));

    	user.removeFavoriteLocation(location);
    	repository.save(user);

    	return ResponseEntity.status(HttpStatus.ACCEPTED).body(
    			new FavouriteLocsResponseDTO(user.getEmail(), List.of(new LocationDTO(
    					location.getAddress(),
    					location.getFictionalAddress(),
    					location.getLatitude(),
    					location.getLongitude()
    				)
   				)
    		)
   		);
    }
}