package com.anju.co2calculator.config;

/**
 * The {@code MessageProvider} class contains predefined static messages used across
 * the CO2 emission calculator application. These messages help standardize error
 * handling and user notifications.
 * 
 * <p>This class cannot be instantiated as it only provides static constants.</p>
 * 
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class MessageProvider {
	
    public static final String NO_ARGUMENTS_PROVIDED = "No arguments provided. Use './co2-calculator --help' to know the correct usage";
    public static final String MISSING_ARGUMENT = "Missing required parameters. Use './co2-calculator --help' to know the correct usage";
    public static final String INVALID_CITY_UNABLE_TO_CALCULATE = "The cities choosen seems invalid. Unable to calculate distance";
    public static final String INVALID_TRANSPORT = "Choosen transportation method is invalid. Use './co2-calculator --help' to know the correct usage";
    public static final String INVALID_SERVICE_KEY = "Unable to create distance calculation service with key '";
    public static final String MISSING_ORS_TOKEN = "Please set the ORS_TOKEN environment variable.";
    public static final String FAILED_REQUEST = "Failed to get coordinates: HTTP error code ";
    public static final String INVALID_START_LOCATION = "Invalid start location. Use './co2-calculator --help' to know the correct usage";
    public static final String INVALID_END_LOCATION = "Invalid end location. Use './co2-calculator --help' to know the correct usage";
    public static final String TRIP_CO2_EMISSION = "-> Your trip caused #CO2_EMISSION_VALUE# kg of CO2 equivalent";
    
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MessageProvider() {
        // To Prevent instantiation
    }
}
