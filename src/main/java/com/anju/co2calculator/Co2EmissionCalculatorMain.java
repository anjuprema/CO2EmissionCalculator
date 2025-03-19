package com.anju.co2calculator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.exception.MissingOrsTokenException;
import com.anju.co2calculator.service.DistanceCalculationFactory;
import com.anju.co2calculator.service.DistanceCalculationInterface;
import com.anju.co2calculator.service.OpenRouteService;
import com.anju.co2calculator.util.CalculateEmissionUtil;

public class Co2EmissionCalculatorMain {
	private static final Map<String, String> arguments = new HashMap<String, String>();

	private static void parseArguments(String args[]) {
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
	}

	private static void validateArguments() {
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
			parseArguments(args);
			validateArguments();

			CalculateEmissionUtil calculateEmission = new CalculateEmissionUtil(DistanceCalculationFactory.distanceCalculationServiceProvider("openRouteService"));
			Double co2EmissionForTripInGm = calculateEmission.calculateCo2ForTrip(arguments.get("--start"),
					arguments.get("--end"), arguments.get("--transportation-method"));
			if (co2EmissionForTripInGm != null) {
				System.out.println("Your trip caused " + String.format("%.1f", co2EmissionForTripInGm / 1000)
						+ " kg of CO2 equivalent");
			}

		} catch (InvalidArgumentException | InvalidCityException | MissingOrsTokenException userException) {
			System.err.println("Error: " + userException.getMessage());
			System.out.println("Anju");
		} catch (IOException ioExeception) {
			System.err.println("Error: " + ioExeception.getMessage());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
