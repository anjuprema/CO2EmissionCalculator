package com.anju.co2calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import com.anju.co2calculator.exception.MissingOrsTokenException;

@ExtendWith(MockitoExtension.class)
public class OpenRouteServiceTest {
	@Mock
	private OpenRouteService mockService;
	
	@Test
	void testInvalidOrsToken() {
		OpenRouteService openRouteService = new OpenRouteService("your_dummy_api_key");
		assertThrows(IOException.class, () -> openRouteService.getDistanceBetweenTwoCities("Berlin", "Munich"));
	}
	
	@Test
	void testOrsTokenNotSet() {
		assertThrows(MissingOrsTokenException.class, () -> new OpenRouteService(null));
	}
	
	@Test
	void testOpenRouteServiceMock() throws Exception {
	    when(mockService.getDistanceBetweenTwoCities("Berlin", "Munich")).thenReturn(585.12);
	    double distance = mockService.getDistanceBetweenTwoCities("Berlin", "Munich");
	    assertEquals(585.12, distance);
	}
}