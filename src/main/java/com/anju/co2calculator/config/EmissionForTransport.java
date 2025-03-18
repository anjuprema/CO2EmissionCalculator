package com.anju.co2calculator.config;

import java.util.HashMap;

public class EmissionForTransport {
    private static final HashMap<String, Integer> averageEmissionPerKm = new HashMap<>();

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

    public static int getAvgEmissionForTransport(String transportationMethod) {
        return averageEmissionPerKm.getOrDefault(transportationMethod, -1);
    }

}
