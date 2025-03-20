package com.anju.co2calculator;

import com.anju.co2calculator.config.EmissionForTransportConfig;
import com.anju.co2calculator.config.MessageProvider;
import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.service.DistanceCalculatorInterface;
/**
 * The {@code EmissionCalculator} class is responsible for calculating CO2 emissions 
 * for a given trip based on the start location, end location, and transportation method.
 * It interacts with a distance calculation service and a transport emission configuration.
 *  
 * <p>Exceptions:</p>
 * <ul>
 *     <li>{@link InvalidArgumentException} - If the transportation method is invalid.</li>
 *     <li>{@link InvalidCityException} - If the city names are invalid or distance cannot be calculated.</li>
 *     <li>{@code Exception} - For any other unexpected errors.</li>
 * </ul>
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class EmissionCalculator {
	private DistanceCalculatorInterface distanceCalculationService;
	
	/**
     * Constructs an {@code EmissionCalculator} with a given distance calculation service.
     *
     * @param distanceCalculationService the service responsible for calculating distances between two locations.
     */
	public EmissionCalculator(DistanceCalculatorInterface distanceCalculationService) {
		this.distanceCalculationService = distanceCalculationService;
	}
	
	/**
     * Calculates the CO2 emissions for a trip between two locations using a specified transport method.
     *
     * @param startLocation  the starting city of the trip.
     * @param endLocation    the destination city of the trip.
     * @param transportMethod the transportation method used for the trip.
     * @return the estimated CO2 emission in grams.
     * @throws InvalidArgumentException if the transport method is invalid.
     * @throws InvalidCityException if the city names are incorrect or distance cannot be determined.
     * @throws Exception for other unexpected errors.
     */
	public Double calculateCo2ForTrip(String startLocation, String endLocation, String transportMethod)
			throws Exception {
		int avgEmissionForTransportInGm = EmissionForTransportConfig.getAvgEmissionForTransport(transportMethod);
		if (avgEmissionForTransportInGm != -1) {
			Double distanceInKm = distanceCalculationService.getDistanceBetweenTwoCities(startLocation, endLocation);
			if (distanceInKm > 0) {
				return distanceInKm * avgEmissionForTransportInGm;
			} else {
				throw new InvalidCityException(MessageProvider.INVALID_CITY_UNABLE_TO_CALCULATE);
			}
		} else {
			throw new InvalidArgumentException(MessageProvider.INVALID_TRANSPORT);
		}
	}
}
