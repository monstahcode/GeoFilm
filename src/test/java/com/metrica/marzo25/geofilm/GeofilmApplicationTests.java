package com.metrica.marzo25.geofilm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.repository.LocationRepository;
import com.metrica.marzo25.geofilm.repository.UserRepository;
import com.metrica.marzo25.geofilm.service.AuthService;
import com.metrica.marzo25.geofilm.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
class GeofilmApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	UserRepository userRepository;
	
	@Test
	@Transactional
	void obtenerBaseDatos() {
	    Location location1 = new Location();
	    location1.setName("Madrid");
	    location1.setAddress("Calle Gran Vía 1");
	    location1.setFictionalAddress("pepeito grillo");
	    location1.setLatitude(40.4168);
	    location1.setLongitude(-3.7038);
	    Location savedLocation1 = locationRepository.save(location1);
	    
	    Location location2 = new Location();
	    location2.setName("Barcelona");
	    location2.setAddress("Las Ramblas 100");
	    location2.setFictionalAddress("mala droga");
	    location2.setLatitude(41.3851);
	    location2.setLongitude(2.1734);
	    Location savedLocation2 = locationRepository.save(location2);
	    
	    User user = new User();
	    user.setUsername("Juan");
	    user.setEmail("yahooo@yahoo.com");
	    user.setPassword(passwordEncoder.encode("123456789"));user.addFavoriteLocation(savedLocation1);
	    user.addFavoriteLocation(savedLocation2);
	    User savedUser = userRepository.save(user);
	    assertNotNull(savedUser.getId());
	    User result = userRepository.findById(savedUser.getId()).orElse(new User());
	    assertNotNull(result);
	    assertEquals("Juan", result.getUsername());
	    assertEquals("yahooo@yahoo.com", result.getEmail());
	    assertEquals(2, result.getFavoriteLocations().size());
	    
	    Set<String> locationNames = result.getFavoriteLocations().stream()
	            .map(Location::getName)
	            .collect(Collectors.toSet());
	    
	    assertTrue(locationNames.contains("Madrid"));
	    assertTrue(locationNames.contains("Barcelona"));
	}
	
	@Test
	@Transactional
	void obtenerBaseDatosConRelacionBidireccional() {
	    Location location1 = new Location();
	    location1.setName("Madrid");
	    location1.setAddress("pepito");
	    location1.setFictionalAddress("pepeito grillo");
	    location1.setLatitude(40.12);
	    location1.setLongitude(40.12);
	    
	    Location location2 = new Location();
	    location2.setName("Barcelona");
	    location2.setAddress("maladona");
	    location2.setFictionalAddress("mala droga");
	    location2.setLatitude(41.38);
	    location2.setLongitude(2.17);
	    Location savedLocation1 = locationRepository.save(location1);
	    Location savedLocation2 = locationRepository.save(location2);
	    
	    User user = new User("Juan", "yahooo@yahoo.com", passwordEncoder.encode("123456789"));
	    user.addFavoriteLocation(savedLocation1);
	    user.addFavoriteLocation(savedLocation2);
	    User savedUser = userRepository.save(user);
	    User userResult = userRepository.findById(savedUser.getId()).orElse(null);
	    assertNotNull(userResult);
	    assertEquals(2, userResult.getFavoriteLocations().size());
	    Location locationResult1 = locationRepository.findById(savedLocation1.getId()).orElse(null);
	    assertNotNull(locationResult1);
	    assertEquals(1, locationResult1.getFavoriteByUsers().size());
	}

	@Test
	@Transactional
	void testFindByIdNot() {
	    Location location1 = new Location();
	    location1.setName("Madrid");
	    location1.setAddress("Calle Alcalá 50");
	    location1.setFictionalAddress("Madrid Fiction");
	    location1.setLatitude(40.4168);
	    location1.setLongitude(-3.7038);
	    Location savedLocation1 = locationRepository.save(location1);
	    
	    Location location2 = new Location();
	    location2.setName("Barcelona");
	    location2.setAddress("Passeig de Gràcia 100");
	    location2.setFictionalAddress("Barcelona Fiction");
	    location2.setLatitude(41.3851);
	    location2.setLongitude(2.1734);
	    Location savedLocation2 = locationRepository.save(location2);
	    
	    Location location3 = new Location();
	    location3.setName("Valencia");
	    location3.setAddress("Calle Colón 25");
	    location3.setFictionalAddress("Valencia Fiction");
	    location3.setLatitude(39.4699);
	    location3.setLongitude(-0.3763);
	    Location savedLocation3 = locationRepository.save(location3);
	    List<Location> allExceptFirst = locationRepository.findByIdNot(savedLocation1.getId());
	    assertEquals(2, allExceptFirst.size());
	    Set<Long> foundIds = allExceptFirst.stream()
	            .map(Location::getId)
	            .collect(Collectors.toSet());
	    assertFalse(foundIds.contains(savedLocation1.getId()));
	    assertTrue(foundIds.contains(savedLocation2.getId()));
	    assertTrue(foundIds.contains(savedLocation3.getId()));
	    Set<String> foundNames = allExceptFirst.stream()
	            .map(Location::getName)
	            .collect(Collectors.toSet());
	    assertFalse(foundNames.contains("Madrid"));
	    assertTrue(foundNames.contains("Barcelona"));
	    assertTrue(foundNames.contains("Valencia"));
	}
}


