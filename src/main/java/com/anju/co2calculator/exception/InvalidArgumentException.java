package com.anju.co2calculator.exception;

/**
 * The {@code InvalidArgumentException} is a custom runtime exception that is 
 * thrown when invalid or missing arguments are provided to the CO2 emission 
 * calculator application.
 * 
 * <p>This exception is typically used to handle cases where required parameters 
 * like {@code --start}, {@code --end}, or {@code --transportation-method} are 
 * not supplied or contain invalid values.</p>
 * 
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class InvalidArgumentException extends RuntimeException {
	/**
     * Constructs a new {@code InvalidArgumentException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
	public InvalidArgumentException(String message) {
		super(message);
	}
}
