package com.anju.co2calculator.util;

import com.anju.co2calculator.config.EmissionForTransport;
import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.service.DistanceCalculationService;


public class CalculateEmission {
	private DistanceCalculationService distanceCalculationService;
	
	public CalculateEmission(DistanceCalculationService distanceCalculationService) {
		this.distanceCalculationService = distanceCalculationService;
	}
	
	public Double calculateCo2ForTrip(String startLocation, String endLocation, String transportMethod) throws Exception {
		int avgEmissionForTransportInGm = EmissionForTransport.getAvgEmissionForTransport(transportMethod);
		if(avgEmissionForTransportInGm != -1) {
			Double distanceInKm = distanceCalculationService.getDistanceBetweenTwoCities(startLocation, endLocation);
			if(distanceInKm > 0) {
				return distanceInKm * avgEmissionForTransportInGm;	
			}else {
				throw new InvalidCityException("The cities choosen seems invalid. Unable to calculate distance");
			}
		} else {
			throw new InvalidArgumentException("Choosen transportation method is invalid");
		}
	}
}
