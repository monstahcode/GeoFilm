package com.metrica.marzo25.geofilm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.service.AuthService;
import com.metrica.marzo25.geofilm.service.UserService;

@SpringBootTest
class GeofilmApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	void contextLoads() {
		System.out.println("HOLA SOY UNA PRUEBA");
		User user = userService.getByEmail("josealvareznunezarenas@gmail.com").orElse(new User());
		System.out.println(passwordEncoder.matches("123456789", user.getPassword()));
		System.out.println(userService.getByEmail("josealvareznunezarenas@gmail.com").orElse(new User()));
		System.out.println(user.toString());
	}

}
