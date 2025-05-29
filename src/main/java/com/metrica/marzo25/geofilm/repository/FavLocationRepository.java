package com.metrica.marzo25.geofilm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metrica.marzo25.geofilm.entity.Location;

public interface FavLocationRepository extends JpaRepository<Location, Long>{
	
}