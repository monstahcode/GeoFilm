-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS geofilm;
USE geofilm;

-- Tabla location
CREATE TABLE location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    fictional_address VARCHAR(50) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    INDEX idx_location_name (name),
    INDEX idx_location_coordinates (latitude, longitude)
);

-- Tabla user
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_username (username),
    INDEX idx_user_email (email)
);

-- Tabla intermedia para la relación muchos a muchos entre user y location
CREATE TABLE user_favorite_locations (
    user_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, location_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES location(id) ON DELETE CASCADE,
    INDEX idx_user_favorites (user_id),
    INDEX idx_location_favorites (location_id)
);

-- Datos de ejemplo para testing
INSERT INTO location (name, address, fictional_address, latitude, longitude) VALUES
('Central Park', '59th Street, New York, NY', 'Parque Central', 40.785091, -73.968285),
('Times Square', 'Times Square, New York, NY', 'Plaza del Tiempo', 40.758896, -73.985130),
('Hollywood Walk of Fame', 'Hollywood Blvd, Los Angeles, CA', 'Paseo de la Fama', 34.101558, -118.340553),
('Golden Gate Bridge', 'Golden Gate Bridge, San Francisco, CA', 'Puente Dorado', 37.819929, -122.478255),
('Millennium Park', '201 E Randolph St, Chicago, IL', 'Parque del Milenio', 41.882702, -87.619392);

INSERT INTO user (username, email, password) VALUES
('admin', 'admin@geofilm.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1TmVJG9IhgU9R8Y7GqP9Zsf6kcIJgtK'),
('user1', 'user1@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1TmVJG9IhgU9R8Y7GqP9Zsf6kcIJgtK'),
('user2', 'user2@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1TmVJG9IhgU9R8Y7GqP9Zsf6kcIJgtK'),
('cinephile', 'movie@lover.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1TmVJG9IhgU9R8Y7GqP9Zsf6kcIJgtK');

-- Relaciones de favoritos de ejemplo
INSERT INTO user_favorite_locations (user_id, location_id) VALUES
(1, 1), -- admin favorita Central Park
(1, 3), -- admin favorita Hollywood Walk of Fame
(2, 1), -- user1 favorita Central Park
(2, 2), -- user1 favorita Times Square
(3, 4), -- user2 favorita Golden Gate Bridge
(3, 5), -- user2 favorita Millennium Park
(4, 1), -- cinephile favorita Central Park
(4, 2), -- cinephile favorita Times Square
(4, 3); -- cinephile favorita Hollywood Walk of Fame

-- Vistas útiles para consultas comunes
CREATE VIEW user_favorites_count AS
SELECT
    u.id as user_id,
    u.username,
    u.email,
    COUNT(ufl.location_id) as total_favorites
FROM user u
LEFT JOIN user_favorite_locations ufl ON u.id = ufl.user_id
GROUP BY u.id, u.username, u.email;

CREATE VIEW location_popularity AS
SELECT
    l.id as location_id,
    l.name,
    l.fictional_address,
    COUNT(ufl.user_id) as times_favorited
FROM location l
LEFT JOIN user_favorite_locations ufl ON l.id = ufl.location_id
GROUP BY l.id, l.name, l.fictional_address
ORDER BY times_favorited DESC;

-- Procedimientos almacenados útiles
DELIMITER //

CREATE PROCEDURE GetUserFavoriteLocations(IN userId BIGINT)
BEGIN
    SELECT
        l.id,
        l.name,
        l.address,
        l.fictional_address,
        l.latitude,
        l.longitude,
        ufl.created_at as favorited_at
    FROM location l
    INNER JOIN user_favorite_locations ufl ON l.id = ufl.location_id
    WHERE ufl.user_id = userId
    ORDER BY ufl.created_at DESC;
END //

CREATE PROCEDURE GetLocationFans(IN locationId BIGINT)
BEGIN
    SELECT
        u.id,
        u.username,
        u.email,
        ufl.created_at as favorited_at
    FROM user u
    INNER JOIN user_favorite_locations ufl ON u.id = ufl.user_id
    WHERE ufl.location_id = locationId
    ORDER BY ufl.created_at DESC;
END //

DELIMITER ;

-- Índices adicionales para optimización
CREATE INDEX idx_location_fictional_address ON location(fictional_address);
CREATE INDEX idx_user_created_at ON user(created_at);
CREATE INDEX idx_favorites_created_at ON user_favorite_locations(created_at);