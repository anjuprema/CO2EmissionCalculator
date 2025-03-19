package com.anju.co2calculator.service;

public class DistanceCalculationFactory {
	public static DistanceCalculationInterface distanceCalculationServiceProvider(String serviceKey) {
		if (serviceKey.equals("openRouteService")) {
			return new OpenRouteService();
		}
		return null;
	}
}
