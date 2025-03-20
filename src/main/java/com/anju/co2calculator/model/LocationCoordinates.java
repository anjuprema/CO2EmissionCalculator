package com.anju.co2calculator.model;

/**
 * Represents the geographical coordinates of a location.
 * This class holds latitude and longitude values.
 */
public class LocationCoordinates {
    private Double latitude;
    private Double longitude;

    /**
     * Constructs a LocationCoordinates object with the specified latitude and longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     */
    public LocationCoordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}