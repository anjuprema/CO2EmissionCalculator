package com.anju.co2calculator.util;

import com.anju.co2calculator.config.EmissionForTransportConfig;
import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.service.DistanceCalculationInterface;

public class CalculateEmissionUtil {
	private DistanceCalculationInterface distanceCalculationService;

	public CalculateEmissionUtil(DistanceCalculationInterface distanceCalculationService) {
		this.distanceCalculationService = distanceCalculationService;
	}

	public Double calculateCo2ForTrip(String startLocation, String endLocation, String transportMethod)
			throws Exception {
		int avgEmissionForTransportInGm = EmissionForTransportConfig.getAvgEmissionForTransport(transportMethod);
		if (avgEmissionForTransportInGm != -1) {
			Double distanceInKm = distanceCalculationService.getDistanceBetweenTwoCities(startLocation, endLocation);
			if (distanceInKm > 0) {
				return distanceInKm * avgEmissionForTransportInGm;
			} else {
				throw new InvalidCityException("The cities choosen seems invalid. Unable to calculate distance");
			}
		} else {
			throw new InvalidArgumentException("Choosen transportation method is invalid");
		}
	}
}
