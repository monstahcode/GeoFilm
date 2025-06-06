package com.metrica.marzo25.geofilm.entity;

import java.util.HashSet;
import java.util.Set;

import com.metrica.marzo25.geofilm.dto.response.LocationDTO;

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

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "fictional_address", nullable = false, length = 50)
    private String fictionalAddress;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToMany(mappedBy = "favoriteLocations", fetch = FetchType.LAZY)
    private Set<User> favoriteByUsers = new HashSet<>();

    public Location() {}

    public Location(LocationDTO locationData) {
    	this.address = locationData.getAddress();
    	this.fictionalAddress = locationData.getFictionalAddress();
        this.latitude = locationData.getLatitude();
        this.longitude = locationData.getLongitude();
    }

    public Location(String address) {
        this.address = address;
    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
