package com.anju.co2calculator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.service.OpenRouteService;
import com.anju.co2calculator.util.CalculateEmission;

public class Co2EmissionCalculatorMain {
    private static Map<String, String> arguments = new HashMap();
	private static boolean validateArguments(String[] args) {
		// Check if arguments are provided
        if (args.length == 0) {
            throw new InvalidArgumentException("No arguments provided. Please specify 'start' 'end' & 'transportation-method'");
        }       

        // Iterate through arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                if (args[i].contains("=")) {
                    String[] parts = args[i].split("=", 2);
                    arguments.put(parts[0], parts[1]);
                } else if (i < args.length-1) {
                    arguments.put(args[i], args[++i]);
                }
            }
        }
        
        // Validate input
        if (arguments.get("--start") == null || arguments.get("--end") == null || arguments.get("--transportation-method") == null) {
        	throw new InvalidArgumentException("Missing one or more required parameters.");
        }        
        return true;
	}
    public static void main(String[] args) {
    	try {
    		boolean isArgsValid = Co2EmissionCalculatorMain.validateArguments(args);    	
            
            if(isArgsValid) {
            	System.out.println("-> Start: " + arguments.get("--start"));
                System.out.println("-> End: " + arguments.get("--end"));
                System.out.println("-> Transportation Method: " + arguments.get("--transportation-method"));
                
                CalculateEmission calculateEmission = new CalculateEmission(new OpenRouteService());
                Double co2EmissionForTripInGm = calculateEmission.calculateCo2ForTrip(arguments.get("--start"), arguments.get("--end"), arguments.get("--transportation-method"));
                if(co2EmissionForTripInGm != null) {
                	System.out.println("Your trip caused "+ String.format("%.1f", co2EmissionForTripInGm / 1000) +" kg of CO2 equivalent");
                }
            }
    	} catch(InvalidArgumentException invalidArg) {
    		System.err.println("Error: "+ invalidArg.getMessage());
    	} catch(InvalidCityException invalidCity) {
    		System.err.println("Error: "+ invalidCity.getMessage());
    	} catch(IOException unableToProcess) {
    		System.err.println("Error: "+ unableToProcess.getMessage());
    	}catch(Exception e) {
    		System.err.println("Error: "+ e.getMessage());
    		e.printStackTrace();
    	}    	        
    }
}
