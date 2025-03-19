package com.anju.co2calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anju.co2calculator.exception.InvalidArgumentException;

public class Co2EmissionCalculatorMainTest {
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // Capture System.out output
    }
    
    @Test
    void testValidArgumentsSeperatedByEquals() {
        String[] args = {"--start=Hamburg", "--end=Berlin", "--transportation-method=diesel-car-medium"};
        assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
        String output = outputStreamCaptor.toString();        
        assertTrue(output.contains("Your trip caused 49.2 kg of CO2 equivalent"));
    }
    
    @Test
    void testValidArgumentsSeperatedBySpace() {
        String[] args = {"--start", "Los Angeles", "--end", "New York", "--transportation-method", "diesel-car-medium"};
        assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
        String output = outputStreamCaptor.toString();  
        assertTrue(output.contains("Your trip caused 770.4 kg of CO2 equivalent"));
    }
    
    @Test
    void testValidArgumentsOrderChanged() {
        String[] args = {"--end", "New York", "--start", "Los Angeles", "--transportation-method=electric-car-large"};
        assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
        String output = outputStreamCaptor.toString();  
        assertTrue(output.contains("Your trip caused 328.9 kg of CO2 equivalent"));
    }
    

    /*
    @Test
    void testMissingArguments() {
    	String[] args = {"--start", "Hamburg", "--transportation-method", "electric-car-large"};        
    	assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
        String output = outputStreamCaptor.toString();  
        assertTrue(output.contains("Missing required parameters"));
    }
     
     void testNoArgumentsProvided() {
        String[] args = {};
        assertThrows(InvalidArgumentException.class, () -> {
            Co2EmissionCalculatorMain.main(args);
        });
    }*/
}
