package com.metrica.marzo25.geofilm.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Entidad User
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // Relación muchos a muchos con Location (localizaciones favoritas)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favorite_locations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> favoriteLocations;

    // Constructores
    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.favoriteLocations = new HashSet<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Location> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(Set<Location> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    // Métodos de conveniencia para manejar favoritos
    public void addFavoriteLocation(Location location) {
        this.favoriteLocations.add(location);
        location.getFavoriteByUsers().add(this);
    }

    public void removeFavoriteLocation(Location location) {
        this.favoriteLocations.remove(location);
        location.getFavoriteByUsers().remove(this);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}