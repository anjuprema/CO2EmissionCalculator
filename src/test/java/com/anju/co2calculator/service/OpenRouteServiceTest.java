package com.anju.co2calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class OpenRouteServiceTest {
	@Test
	void testGetDistanceBetweenTwoCities() {
		OpenRouteService openRouteService = new OpenRouteService("5b3ce3597851110001cf6248a9d9d403130a44248f5f89958c26cd58");
		double distance;
		try {
			distance = openRouteService.getDistanceBetweenTwoCities("Berlin", "Munich");
		} catch (Exception e) {
			assertFalse(true);
			return;
		}
		assertEquals(585.12, distance);
	}

	@Test
	void testGetDistanceBetweenTwoCitiesForException() {
		OpenRouteService openRouteService = new OpenRouteService("5b3ce3597851110001cf6248a9d9d403130a44248f5f89958c26cd58");
		assertThrows(Exception.class, () -> openRouteService.getDistanceBetweenTwoCities("abcd", "Munich"));
	}
}