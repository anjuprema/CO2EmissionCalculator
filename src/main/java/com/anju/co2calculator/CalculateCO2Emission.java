package com.anju.co2calculator;

import com.anju.co2calculator.service.DistanceCalcuationService;
import com.anju.co2calculator.service.OpenRouteService;

public class CalculateCO2Emission {
	private DistanceCalcuationService distanceCalculationService;
	
	public CalculateCO2Emission(DistanceCalcuationService distanceCalculationService) {
		this.distanceCalculationService = distanceCalculationService;
	}
	
	public Double calculateCo2ForTrip(String startLocation, String endLocation, String transportMethod) {

		Double distanceInKm = distanceCalculationService.getDistanceBetweenTwoCities(startLocation, endLocation);
		int avgEmissionForTransportInGm = EmissionForTransportPerKilometer.getAvgEmissionForTransport(transportMethod);
		if(avgEmissionForTransportInGm != -1) {
			Double co2EmissionForTripInGm = distanceInKm * avgEmissionForTransportInGm; // in grams
			return co2EmissionForTripInGm;
		}else {
			System.out.println("Choose correct Transportation Method");
			return null;
		}
	}
}
