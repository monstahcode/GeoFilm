package com.metrica.marzo25.geofilm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.metrica.marzo25.geofilm.service.UserService;

@SpringBootTest
class GeofilmApplicationTests {

	@Autowired
	UserService service;
	
	@Test
	void contextLoads() {
		System.out.println(service.login("josealvareznunezarenas@gmail.com", "123456789"));
	}

}
