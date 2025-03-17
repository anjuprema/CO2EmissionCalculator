package com.anju.co2calculator;

import java.util.HashMap;

public class EmissionForTransportPerKilometer {
    private static final HashMap<String, Integer> averageEmissionForTransport = new HashMap<>();

    static {
    	averageEmissionForTransport.put("diesel-car-small", 142);
    	averageEmissionForTransport.put("petrol-car-small", 154);
    	averageEmissionForTransport.put("plugin-hybrid-car-small", 73);
    	averageEmissionForTransport.put("electric-car-small", 50);
    	averageEmissionForTransport.put("diesel-car-medium", 171);
    	averageEmissionForTransport.put("petrol-car-medium", 192);
    	averageEmissionForTransport.put("plugin-hybrid-car-medium", 110);
    	averageEmissionForTransport.put("electric-car-medium", 58);
        averageEmissionForTransport.put("diesel-car-large", 209);
        averageEmissionForTransport.put("petrol-car-large", 282);
        averageEmissionForTransport.put("plugin-hybrid-car-large", 126);
        averageEmissionForTransport.put("electric-car-large", 73);
        averageEmissionForTransport.put("bus-default", 27);
        averageEmissionForTransport.put("train-default", 6);
    }

    public static int getAvgEmissionForTransport(String transportationMethod) {
        return averageEmissionForTransport.getOrDefault(transportationMethod, -1);
    }

}
