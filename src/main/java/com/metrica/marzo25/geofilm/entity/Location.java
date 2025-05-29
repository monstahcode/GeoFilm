package com.metrica.marzo25.geofilm.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "fictional_address", nullable = false, length = 50)
    private String fictionalAddress;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // Relación muchos a muchos con User (usuarios que tienen esta localización como favorita)
    @ManyToMany(mappedBy = "favoriteLocations", fetch = FetchType.LAZY)
    private Set<User> favoriteByUsers = new HashSet<>();

    // Constructores
    public Location() {}

    public Location(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
    }

    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    // Getters y Setters
   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFictionalAddress() {
		return fictionalAddress;
	}

	public void setFictionalAddress(String fictionalAddress) {
		this.fictionalAddress = fictionalAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Set<User> getFavoriteByUsers() {
		return favoriteByUsers;
	}

	public void setFavoriteByUsers(Set<User> favoriteByUsers) {
		this.favoriteByUsers = favoriteByUsers;
	}
	
    
    
    
}
