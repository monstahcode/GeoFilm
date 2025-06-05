package com.metrica.marzo25.geofilm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrica.marzo25.geofilm.dto.response.FavouriteLocsResponseDTO;
import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.extra.MediaLocation;
import com.metrica.marzo25.geofilm.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class ProfileController {
	
	private UserService service;

	@Autowired
	public ProfileController(UserService service) {
		this.service = service;
	}
	
	@PostMapping("/email")
	public Optional<User> getByEmail(@RequestBody String email){
		return service.getByEmail(email);
	}
	
	@PostMapping("/id")
	public Optional<User> getById(@RequestBody Long id){
		return service.getById(id);
	}
	
	@PostMapping("/favlocs/add")
    public ResponseEntity<FavouriteLocsResponseDTO> addFavoriteLocation(@RequestBody String email, @RequestBody Double[] crds) {
        return service.addFavouriteLocs(email, crds);
    }
	
	@DeleteMapping("/favlocs/remove")
    public ResponseEntity<FavouriteLocsResponseDTO> removeFavoriteLocation(@RequestBody String email, @RequestBody Double[] crds) {
        return service.removeFavouriteLocs(email, crds);
    }
	
	@PostMapping("/favlocs/list")
    public ResponseEntity<FavouriteLocsResponseDTO> getFavoriteLocations(@RequestBody String email) {
        return service.getFavouriteLocs(email);
    }
	
	public User saveUser(User user) {
		return service.saveUser(user);
	}
	
}
