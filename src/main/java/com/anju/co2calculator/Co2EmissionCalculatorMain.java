package com.anju.co2calculator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anju.co2calculator.config.MessageProvider;
import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.exception.MissingOrsTokenException;
import com.anju.co2calculator.service.DistanceCalculatorServiceFactory;

/**
 * Main class for CO2 Emission Calculator.
 * This class parses user arguments, validates inputs, and calculates CO2 emissions
 * for a trip based on the transportation method and city details.
 * 
 * Usage Example:
 * <pre>
 * From /scripts path execute
 * ./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
 * </pre>
 * 
 * Expected Output:
 * <pre>
 * Your trip caused 49.2kg of CO2-equivalent
 * </pre>
 * 
 * Exceptions:
 * - {@link InvalidArgumentException} if required arguments are missing.
 * - {@link InvalidCityException} if an invalid city is provided.
 * - {@link MissingOrsTokenException} if the ORS API token is not set.
 * - {@link IOException} if there is an issue with API communication.
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class Co2EmissionCalculatorMain {
	/**
     * Parses command-line arguments and stores them in a map.
     * 
     * @param args Command-line arguments provided by the user.
     * @return A map containing argument keys and values.
     */
	private static Map<String, String> parseArguments(String args[]) {
		Map<String, String> arguments = new HashMap<String, String>();
		/* Iterate through arguments and retrieve values */
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--")) {
				if (args[i].contains("=")) {
					String[] parts = args[i].split("=", 2);
					arguments.put(parts[0], parts[1]);
				} else if (i < args.length - 1) {
					arguments.put(args[i], args[++i]);
				}
			}
		}
		return arguments;
	}
	
	/**
     * Validates if all required arguments are provided.
     * 
     * @param arguments The parsed arguments map.
     * @throws InvalidArgumentException if required arguments are missing.
     */
	private static void validateArguments(Map<String, String> arguments) {
		/* Check if any arguments are provided */
		if (arguments.isEmpty()) {
			throw new InvalidArgumentException(MessageProvider.NO_ARGUMENTS_PROVIDED);
		}

		/* Check if any required arguments are missing */
		if (arguments.get("--start") == null || arguments.get("--end") == null
				|| arguments.get("--transportation-method") == null) {
			throw new InvalidArgumentException(MessageProvider.MISSING_ARGUMENT);
		}
	}
	
	/**
     * Main method to execute the CO2 emission calculation.
     * 
     * @param args Command-line arguments for CO2 emission calculation.
     */
	public static void main(String[] args) {
		try {
			Map<String, String> arguments = new HashMap<String, String>();
			arguments = parseArguments(args);
			validateArguments(arguments);

			EmissionCalculator calculateEmissionUtil = new EmissionCalculator(DistanceCalculatorServiceFactory.distanceCalculationServiceProvider("openRouteService"));
			Double co2EmissionForTripInGm = calculateEmissionUtil.calculateCo2ForTrip(arguments.get("--start"),
					arguments.get("--end"), arguments.get("--transportation-method"));
			if (co2EmissionForTripInGm != null) {				
				System.out.println(MessageProvider.TRIP_CO2_EMISSION.replace("#CO2_EMISSION_VALUE#", String.format("%.1f", co2EmissionForTripInGm / 1000)));
			}

		} catch (InvalidArgumentException | InvalidCityException | MissingOrsTokenException userException) {
			System.err.println("Error: " + userException.getMessage());
		} catch (IOException ioExeception) {
			System.err.println("Error: " + ioExeception.getMessage());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
