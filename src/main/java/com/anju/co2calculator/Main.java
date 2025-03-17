package com.anju.co2calculator;

import java.util.HashMap;
import java.util.Map;

import com.anju.co2calculator.service.OpenRouteService;

public class Main {
    public static void main(String[] args) {
    	// Check if arguments are provided
        if (args.length == 0) {
            System.out.println("No arguments provided. Please specify options.");
            return;
        }

        // Map to store parsed arguments
        Map<String, String> arguments = new HashMap();

        // Iterate through arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {            	
                if (args[i].contains("=")) {
                    String[] parts = args[i].split("=", 2);
                    arguments.put(parts[0], parts[1]);
                } else if (i <= args.length) {
                    arguments.put(args[i], args[++i]);
                }
            }
        }

        // Validate input
        if (arguments.get("--start") == null || arguments.get("--end") == null || arguments.get("--transportation-method") == null) {
            System.out.println("Missing one or more required parameters.");
            return;
        }

        // Print results
        System.out.println("Start: " + arguments.get("--start"));
        System.out.println("End: " + arguments.get("--end"));
        System.out.println("Transportation Method: " + arguments.get("--transportation-method"));
        
        CalculateCO2Emission calculateEmission = new CalculateCO2Emission(new OpenRouteService());
        Double co2EmissionForTripInGm = calculateEmission.calculateCo2ForTrip(arguments.get("--start"), arguments.get("--end"), arguments.get("--transportation-method"));
        if(co2EmissionForTripInGm != null) {
        	System.out.println("Your trip caused "+ String.format("%.1f", co2EmissionForTripInGm / 1000) +" kg of CO2 equivalent");
        }
    }
}
