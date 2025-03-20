package com.anju.co2calculator.service;

import com.anju.co2calculator.config.MessageProvider;
import com.anju.co2calculator.exception.InvalidArgumentException;
/**
 * The {@code DistanceCalculatorServiceFactory} provides an instance of 
 * a distance calculation service based on the given service key.
 * 
 * <p>This factory class ensures that the appropriate implementation of 
 * {@code DistanceCalculatorInterface} is returned based on the provided service key.</p>
 * 
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class DistanceCalculatorServiceFactory {
	/**
     * Provides an instance of a distance calculation service based on the given service key.
     * 
     * @param serviceKey the identifier for the distance calculation service.
     * @return an instance of {@code DistanceCalculatorInterface} corresponding to the provided key.
     * @throws InvalidArgumentException if the given service key is invalid or not supported.
     */
	public static DistanceCalculatorInterface distanceCalculationServiceProvider(String serviceKey) {
		if (serviceKey.equals("openRouteService")) {
			return new OpenRouteService();
		} else {
			throw new InvalidArgumentException(MessageProvider.INVALID_SERVICE_KEY+serviceKey+"'");
		}
	}
}
