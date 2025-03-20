package com.anju.co2calculator.service;

/**
 * The {@code DistanceCalculatorInterface} defines a contract for calculating 
 * the distance between two cities.
 * 
 * <p>Implementing classes should provide logic to fetch or compute 
 * the distance between the given start and end locations.</p>
 * 
 * 
 * @author Anju Prema
 * @version 1.0
 */
public interface DistanceCalculatorInterface {
	/**
     * Calculates the distance between two cities.
     * 
     * @param start the name of the starting city.
     * @param end the name of the destination city.
     * @return the distance in kilometers between the two cities.
     * @throws Exception if an error occurs while fetching or computing the distance.
     */
	double getDistanceBetweenTwoCities(String start, String end) throws Exception;
}
