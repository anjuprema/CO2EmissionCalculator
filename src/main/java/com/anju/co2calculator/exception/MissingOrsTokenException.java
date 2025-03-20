package com.anju.co2calculator.exception;

/**
 * The {@code MissingOrsTokenException} is a custom runtime exception that is 
 * thrown when the required OpenRouteService (ORS) API token is not set in 
 * the environment variables.
 * 
 * <p>This exception ensures that users provide the necessary API token for 
 * distance calculations to function properly.</p>
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class MissingOrsTokenException extends RuntimeException {
	/**
     * Constructs a new {@code MissingOrsTokenException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
	public MissingOrsTokenException(String message) {
		super(message);
	}
}
