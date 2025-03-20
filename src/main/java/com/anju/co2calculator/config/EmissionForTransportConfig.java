package com.anju.co2calculator.config;

import java.util.HashMap;
/**
 * The {@code EmissionForTransportConfig} class stores the average CO2 emissions per kilometer
 * for different types of transportation methods. It provides a method to retrieve emission
 * values based on the selected transport type.
 * 
 * <p>Supported Transport Types and Their CO2 Emissions (in g/km):</p>
 * <ul>
 *     <li>diesel-car-small - 142 g/km</li>
 *     <li>petrol-car-small - 154 g/km</li>
 *     <li>plugin-hybrid-car-small - 73 g/km</li>
 *     <li>electric-car-small - 50 g/km</li>
 *     <li>diesel-car-medium - 171 g/km</li>
 *     <li>petrol-car-medium - 192 g/km</li>
 *     <li>plugin-hybrid-car-medium - 110 g/km</li>
 *     <li>electric-car-medium - 58 g/km</li>
 *     <li>diesel-car-large - 209 g/km</li>
 *     <li>petrol-car-large - 282 g/km</li>
 *     <li>plugin-hybrid-car-large - 126 g/km</li>
 *     <li>electric-car-large - 73 g/km</li>
 *     <li>bus-default - 27 g/km</li>
 *     <li>train-default - 6 g/km</li>
 * </ul>
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class EmissionForTransportConfig {
	/**
     * A static map that stores average CO2 emissions (g/km) for each transport method.
     */
	private static final HashMap<String, Integer> averageEmissionPerKm = new HashMap<String, Integer>();
	
	// Static block to initialize transport emission values
	static {
		averageEmissionPerKm.put("diesel-car-small", 142);
		averageEmissionPerKm.put("petrol-car-small", 154);
		averageEmissionPerKm.put("plugin-hybrid-car-small", 73);
		averageEmissionPerKm.put("electric-car-small", 50);
		averageEmissionPerKm.put("diesel-car-medium", 171);
		averageEmissionPerKm.put("petrol-car-medium", 192);
		averageEmissionPerKm.put("plugin-hybrid-car-medium", 110);
		averageEmissionPerKm.put("electric-car-medium", 58);
		averageEmissionPerKm.put("diesel-car-large", 209);
		averageEmissionPerKm.put("petrol-car-large", 282);
		averageEmissionPerKm.put("plugin-hybrid-car-large", 126);
		averageEmissionPerKm.put("electric-car-large", 73);
		averageEmissionPerKm.put("bus-default", 27);
		averageEmissionPerKm.put("train-default", 6);
	}

	/**
     * Retrieves the average CO2 emission (in g/km) for a given transportation method.
     *
     * @param transportationMethod The type of transport (e.g., "diesel-car-medium").
     * @return The average CO2 emission in grams per kilometer, or -1 if the method is not found.
     */
	public static int getAvgEmissionForTransport(String transportationMethod) {
		return averageEmissionPerKm.getOrDefault(transportationMethod, -1);
	}
}
