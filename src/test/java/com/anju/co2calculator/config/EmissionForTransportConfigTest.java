package com.anju.co2calculator.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmissionForTransportConfigTest {
	
	@Test
    void testAllTransportationMethodsReturnsValue() {
        String[] transportationMethods = {
            "diesel-car-small", "petrol-car-small", "plugin-hybrid-car-small", "electric-car-small",
            "diesel-car-medium", "petrol-car-medium", "plugin-hybrid-car-medium", "electric-car-medium",
            "diesel-car-large", "petrol-car-large", "plugin-hybrid-car-large", "electric-car-large",
            "bus-default", "train-default"
        };

        for (String method : transportationMethods) {
            assertTrue(EmissionForTransportConfig.getAvgEmissionForTransport(method) > 0);
        }
    }
	
	@Test
    void testValidTransportationMethodValues() {
        assertEquals(142, EmissionForTransportConfig.getAvgEmissionForTransport("diesel-car-small"));
        assertEquals(154, EmissionForTransportConfig.getAvgEmissionForTransport("petrol-car-small"));
        assertEquals(50, EmissionForTransportConfig.getAvgEmissionForTransport("electric-car-small"));
        assertEquals(192, EmissionForTransportConfig.getAvgEmissionForTransport("petrol-car-medium"));
        assertEquals(6, EmissionForTransportConfig.getAvgEmissionForTransport("train-default"));
    }
	
	@Test
    void testInvalidTransportationMethod() {
        assertEquals(-1, EmissionForTransportConfig.getAvgEmissionForTransport("new_unknown-vehicle"));
        assertEquals(-1, EmissionForTransportConfig.getAvgEmissionForTransport(""));
        assertEquals(-1, EmissionForTransportConfig.getAvgEmissionForTransport(null));
    }
	
	
}
