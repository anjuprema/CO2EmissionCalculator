package com.anju.co2calculator.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.anju.co2calculator.config.MessageProvider;
import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.exception.MissingOrsTokenException;

/**
 * The {@code OpenRouteService} class provides functionality for calculating the
 * distance between two cities using the OpenRouteService API.
 * 
 * <p>It fetches the geographical coordinates of the cities, and then calculates
 * the driving distance between them using the OpenRouteService API.</p>
 * 
 * <p>This class requires an API key, which can be passed either through the 
 * environment variable ORS_TOKEN or by providing it explicitly.</p>
 * 
 * <p>Usage Example:</p>
 * <pre>
 * OpenRouteService ors = new OpenRouteService("your-api-key");
 * double distance = ors.getDistanceBetweenTwoCities("New York", "Los Angeles");
 * </pre>
 * 
 * @see DistanceCalculatorInterface
 * @see com.anju.co2calculator.exception.InvalidCityException
 * @see com.anju.co2calculator.exception.MissingOrsTokenException
 * 
 * @author Anju Prema
 * @version 1.0
 */
public class OpenRouteService implements DistanceCalculatorInterface {
	// API key for authentication with OpenRouteService
	private String apiKey;
	private static final String baseURL = "https://api.openrouteservice.org/";
	private URL requestURL;
	private HttpURLConnection connection;
	
	/**
     * Constructor that initializes the service with the API key from environment variable.
     * 
     * @throws MissingOrsTokenException if the ORS_TOKEN environment variable is not set.
     */
	public OpenRouteService() {
		apiKey = System.getenv("ORS_TOKEN");
		if (apiKey == null || apiKey.trim().isEmpty()) {
			throw new MissingOrsTokenException(MessageProvider.MISSING_ORS_TOKEN);
		}
	}
	
	/**
     * Constructor that initializes the service with a provided API key.
     * Helps run Junit test
     * 
     * @param apiKey the API key for OpenRouteService authentication.
     * @throws MissingOrsTokenException if the provided API key is null or empty.
     */
	public OpenRouteService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new MissingOrsTokenException(MessageProvider.MISSING_ORS_TOKEN);
        }
        this.apiKey = apiKey;
    }

	/**
     * Creates a HTTP connection for API requests.
     * 
     * @param requestMethod the HTTP request method (e.g., GET, POST).
     * @param contentType the content type of the request (e.g., "application/json").
     * @return a HttpURLConnection object.
     * @throws Exception if an error occurs during the connection setup.
     */
	private HttpURLConnection createHttpConnection(String requestMethod, String contentType) throws Exception {
		connection = (HttpURLConnection) requestURL.openConnection();
		connection.setRequestMethod(requestMethod);
		if (contentType != null) {
			connection.setRequestProperty("Authorization", apiKey);
			connection.setRequestProperty("Content-Type", contentType);
		}
		connection.setDoOutput(true);
		return connection;
	}

	/**
     * Retrieves the geographic coordinates (latitude and longitude) of a given city.
     * 
     * @param cityName the name of the city for which to get coordinates.
     * @return a HashMap containing "latitude" and "longitude" of the city.
     * @throws Exception if there is an issue with the API request or the response.
     */
	private HashMap<String, Double> getCoordinatesOfCity(String cityName) throws Exception {
		requestURL = new URL(baseURL + "geocode/search?api_key=" + apiKey + "&layers=locality&text="
				+ URLEncoder.encode(cityName, StandardCharsets.UTF_8));
		connection = createHttpConnection("GET", null);

		/* Check if data can be read successfully */
		int responseCode = connection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException(MessageProvider.FAILED_REQUEST + responseCode);
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONArray features = jsonResponse.getJSONArray("features");
			HashMap<String, Double> responseCoordinates = new HashMap<>();
			if (features.length() > 0) {
				JSONObject geometry = features.getJSONObject(0).getJSONObject("geometry");
				JSONArray coordinates = geometry.getJSONArray("coordinates");
				responseCoordinates.put("longitude", coordinates.getDouble(0));
				responseCoordinates.put("latitude", coordinates.getDouble(1));
			}
			return responseCoordinates;

		} finally {
			connection.disconnect();
		}
	}

	/**
     * Calculates the driving distance between two cities using OpenRouteService's API.
     * 
     * @param startLocation the name of the starting city.
     * @param endLocation the name of the destination city.
     * @return the distance in kilometers between the two cities.
     * @throws Exception if there is an issue with the city names or the API request.
     */
	public double getDistanceBetweenTwoCities(String startLocation, String endLocation) throws Exception {
		HashMap<String, Double> cityCoordinates = getCoordinatesOfCity(startLocation);
		Double latitudeStartLocation = cityCoordinates.get("latitude");
		Double longitudeStartLocation = cityCoordinates.get("longitude");

		if (latitudeStartLocation == null || longitudeStartLocation == null) {
			throw new InvalidCityException(MessageProvider.INVALID_START_LOCATION);
		}

		cityCoordinates = getCoordinatesOfCity(endLocation);
		Double latitudeEndLocation = cityCoordinates.get("latitude");
		;
		Double longitudeEndLocation = cityCoordinates.get("longitude");

		if (latitudeEndLocation == null || longitudeEndLocation == null) {
			throw new InvalidCityException(MessageProvider.INVALID_END_LOCATION);
		}

		requestURL = new URL(baseURL + "v2/matrix/driving-car");
		// JSON request body
		JSONObject requestBody = new JSONObject();
		requestBody.put("locations",
				new JSONArray(Arrays.asList(new JSONArray(Arrays.asList(longitudeStartLocation, latitudeStartLocation)),
						new JSONArray(Arrays.asList(longitudeEndLocation, latitudeEndLocation)))));
		requestBody.put("metrics", new JSONArray(Arrays.asList("distance")));
		requestBody.put("units", "km");

		// Send Request
		connection = createHttpConnection("POST", "application/json");
		connection.setDoOutput(true);
		try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
			writer.writeBytes(requestBody.toString());
			writer.flush();
		}

		/* Check if data can be read successfully */
		int responseCode = connection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException(MessageProvider.FAILED_REQUEST + responseCode);
		}

		// Read the response
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			// Parse JSON response
			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONArray distances = jsonResponse.getJSONArray("distances").getJSONArray(0);
			double distance = 0;
			if (!distances.isNull(1)) {
				distance = distances.getDouble(1);
			}
			return distance;
		} finally {
			connection.disconnect();
		}
	}
}
