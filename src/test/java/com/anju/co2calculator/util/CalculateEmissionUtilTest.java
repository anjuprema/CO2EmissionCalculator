package com.anju.co2calculator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import com.anju.co2calculator.config.EmissionForTransportConfig;
import com.anju.co2calculator.exception.InvalidArgumentException;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.service.DistanceCalculationInterface;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculateEmissionUtilTest {
	@Mock
    private DistanceCalculationInterface distanceCalculationService;
	
	@InjectMocks
    private CalculateEmissionUtil calculateEmission;

    @Test
    void testCalculateCo2ForTripForValidData() throws Exception {

        when(distanceCalculationService.getDistanceBetweenTwoCities("New York", "Los Angeles")).thenReturn(4000.0);
        try (MockedStatic<EmissionForTransportConfig> mockedStaticTransport = mockStatic(EmissionForTransportConfig.class)) {
        	mockedStaticTransport.when(() -> EmissionForTransportConfig.getAvgEmissionForTransport("diesel-car-medium")).thenReturn(171);
        	Double result = calculateEmission.calculateCo2ForTrip("New York", "Los Angeles", "diesel-car-medium");
            assertNotNull(result);
            assertEquals(684000.0, result);
        }        
    }
    
    @Test
    void testCalculateCo2ForTripForInvalidCity() throws Exception {
		when(distanceCalculationService.getDistanceBetweenTwoCities("Unknown City", "Los Angeles")).thenReturn(0.0);
		Exception exception = assertThrows(InvalidCityException.class, () -> 
        	calculateEmission.calculateCo2ForTrip("Unknown City", "Los Angeles", "diesel-car-medium")
        );

        assertEquals("The cities choosen seems invalid. Unable to calculate distance", exception.getMessage());        
    }
    
    @Test
    void testCalculateCo2ForTripForInvalidTransportMethod() throws Exception {
        try (MockedStatic<EmissionForTransportConfig> mockedStaticTransport = mockStatic(EmissionForTransportConfig.class)) {
        	mockedStaticTransport.when(() -> EmissionForTransportConfig.getAvgEmissionForTransport("invalid-transport")).thenReturn(-1);
        	Exception exception = assertThrows(InvalidArgumentException.class, () -> 
	            calculateEmission.calculateCo2ForTrip("New York", "Los Angeles", "invalid-transport")
	        );	
	        assertEquals("Choosen transportation method is invalid", exception.getMessage());
        }        
    }
}
