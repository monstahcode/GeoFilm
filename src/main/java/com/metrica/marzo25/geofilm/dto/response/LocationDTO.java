package com.metrica.marzo25.geofilm.dto.response;

public class LocationDTO {
    private String address;
    private String fictionalAddress;
    private Double latitude;
    private Double longitude;

    public LocationDTO() {}

    public LocationDTO(String address, String fictionalAddress, Double latitude, Double longitude) {
        this.address = address;
        this.fictionalAddress = fictionalAddress;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
