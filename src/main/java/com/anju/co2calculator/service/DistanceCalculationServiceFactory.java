package com.anju.co2calculator.service;

import com.anju.co2calculator.exception.InvalidArgumentException;

public class DistanceCalculationServiceFactory {
	public static DistanceCalculationInterface distanceCalculationServiceProvider(String serviceKey) {
		if (serviceKey.equals("openRouteService")) {
			return new OpenRouteService();
		} else {
			throw new InvalidArgumentException("Unable to create distance calculation service with key '"+serviceKey+"'");
		}
	}
}
