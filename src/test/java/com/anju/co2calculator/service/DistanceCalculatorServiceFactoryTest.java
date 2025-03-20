package com.anju.co2calculator.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.anju.co2calculator.exception.InvalidArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistanceCalculatorServiceFactoryTest {
	@Mock
	private OpenRouteService openRouteServiceMock;
    
	@Test
    void testValidServiceBasedOnKey() {
		try (MockedStatic<DistanceCalculatorServiceFactory> distanceCalculationFactoryMock = mockStatic(DistanceCalculatorServiceFactory.class)) {
			distanceCalculationFactoryMock.when(() -> DistanceCalculatorServiceFactory.distanceCalculationServiceProvider("openRouteService"))
                    .thenReturn(openRouteServiceMock);
        	DistanceCalculatorInterface openRouteService = DistanceCalculatorServiceFactory.distanceCalculationServiceProvider("openRouteService");
        	assertNotNull(openRouteService);
        	assertInstanceOf(OpenRouteService.class, openRouteService);
		}
    }
	
	@Test
    void testInvalidServiceBasedOnKey() {
        assertThrows(InvalidArgumentException.class, () -> {
            DistanceCalculatorServiceFactory.distanceCalculationServiceProvider("invalidServiceKey");
        });
    }
}
