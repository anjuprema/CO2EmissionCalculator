package com.anju.co2calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.anju.co2calculator.service.DistanceCalculatorInterface;
import com.anju.co2calculator.service.DistanceCalculatorServiceFactory;

@ExtendWith(MockitoExtension.class)
public class Co2EmissionCalculatorMainTest {
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();

	@Mock
	private DistanceCalculatorInterface distanceCalculationService;

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
		System.setErr(new PrintStream(errorStreamCaptor));
	}

	@Test
	void testValidArgumentsSeperatedByEquals() throws Exception {
		String[] args = { "--start=Hamburg", "--end=Berlin", "--transportation-method=diesel-car-medium" };
		try (MockedStatic<DistanceCalculatorServiceFactory> openRouteServiceMock = mockStatic(
				DistanceCalculatorServiceFactory.class);
				MockedConstruction<EmissionCalculator> calculateEmissionUtilMock = mockConstruction(
						EmissionCalculator.class,
						(mock, context) -> when(mock.calculateCo2ForTrip("Hamburg", "Berlin", "diesel-car-medium"))
								.thenReturn(4000.0))) {
			openRouteServiceMock.when(
					() -> DistanceCalculatorServiceFactory.distanceCalculationServiceProvider("openRouteService"))
					.thenReturn(distanceCalculationService);
			assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
			String output = outputStreamCaptor.toString();
			assertTrue(output.contains("Your trip caused 4.0 kg of CO2 equivalent"));
		}
	}

	@Test
	void testMissingArguments() {
		String[] args = { "--start=Hamburg", "--transportation-method=diesel-car-medium" };
		assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
		String errorOutput = errorStreamCaptor.toString();
		assertTrue(errorOutput.contains(
				"Error: Missing required parameters. Ensure you provide --start, --end, and --transportation-method."));
	}

	@Test
	void testNoArgumentsProvided() {
		String[] args = {};
		assertDoesNotThrow(() -> Co2EmissionCalculatorMain.main(args));
		String errorOutput = errorStreamCaptor.toString();
		assertTrue(errorOutput.contains(
				"Error: No arguments provided. Ensure you provide --start, --end, and --transportation-method."));
	}
}
