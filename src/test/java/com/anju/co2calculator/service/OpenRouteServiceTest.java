package com.anju.co2calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import com.anju.co2calculator.exception.MissingOrsTokenException;

public class OpenRouteServiceTest {
	@Test
	void testInvalidOrsToken() {
		OpenRouteService openRouteService = new OpenRouteService("your_dummy_api_key");
		assertThrows(IOException.class, () -> openRouteService.getDistanceBetweenTwoCities("Berlin", "Munich"));
	}
	
	@Test
	void testOrsTokenNotSet() {
		assertThrows(MissingOrsTokenException.class, () -> new OpenRouteService());
	}
}