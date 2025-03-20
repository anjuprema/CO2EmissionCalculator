package com.anju.co2calculator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.exception.MissingOrsTokenException;
import com.anju.co2calculator.service.DistanceCalculationServiceFactory;
import com.anju.co2calculator.service.DistanceCalculationInterface;
import com.anju.co2calculator.service.OpenRouteService;
import com.anju.co2calculator.util.CalculateEmissionUtil;

public class Co2EmissionCalculatorMain {
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

	private static void validateArguments(Map<String, String> arguments) {
		/* Check if any arguments are provided */
		if (arguments.isEmpty()) {
			throw new InvalidArgumentException(
					"No arguments provided. Ensure you provide --start, --end, and --transportation-method.");
		}

		/* Check if any required arguments are missing */
		if (arguments.get("--start") == null || arguments.get("--end") == null
				|| arguments.get("--transportation-method") == null) {
			throw new InvalidArgumentException(
					"Missing required parameters. Ensure you provide --start, --end, and --transportation-method.");
		}
	}

	public static void main(String[] args) {
		try {
			Map<String, String> arguments = new HashMap<String, String>();
			arguments = parseArguments(args);
			validateArguments(arguments);

			CalculateEmissionUtil calculateEmissionUtil = new CalculateEmissionUtil(DistanceCalculationServiceFactory.distanceCalculationServiceProvider("openRouteService"));
			Double co2EmissionForTripInGm = calculateEmissionUtil.calculateCo2ForTrip(arguments.get("--start"),
					arguments.get("--end"), arguments.get("--transportation-method"));
			if (co2EmissionForTripInGm != null) {
				System.out.println("Your trip caused " + String.format("%.1f", co2EmissionForTripInGm / 1000)
						+ " kg of CO2 equivalent");
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
