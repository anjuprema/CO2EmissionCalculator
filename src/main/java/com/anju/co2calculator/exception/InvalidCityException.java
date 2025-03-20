package com.anju.co2calculator.exception;

/**
 * The {@code InvalidCityException} is a custom runtime exception that is 
 * thrown when an invalid city name is provided, preventing the distance 
 * calculation between locations.
 * 
 * <p>This exception is typically used in scenarios where the provided city 
 * names do not exist or cannot be processed by the distance calculation service.</p>
 * 
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class InvalidCityException extends RuntimeException {
	 /**
	 * Constructs a new {@code InvalidCityException} with the specified detail message.
	 *
	 * @param message the detail message explaining the reason for the exception.
	 */
	public InvalidCityException(String message) {
		super(message);
	}
}
