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

import com.anju.co2calculator.exception.InvalidCityException;
import com.anju.co2calculator.exception.MissingOrsTokenException;

public class OpenRouteService implements DistanceCalculationInterface {
	private static final String apiKey = System.getenv("ORS_TOKEN");
	private static final String baseURL = "https://api.openrouteservice.org/";
	private URL requestURL;
	private HttpURLConnection connection;

	public OpenRouteService() {
		if (apiKey == null || apiKey.trim().isEmpty()) {
			throw new MissingOrsTokenException("Please set the ORS_TOKEN environment variable.");
		}
	}

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

	private HashMap<String, Double> getCoordinatesOfCity(String cityName) throws Exception {
		requestURL = new URL(baseURL + "geocode/search?api_key=" + apiKey + "&layers=locality&text="
				+ URLEncoder.encode(cityName, StandardCharsets.UTF_8));
		connection = createHttpConnection("GET", null);

		/* Check if data can be read successfully */
		int responseCode = connection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException("Failed to get coordinates: HTTP error code " + responseCode);
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

	public double getDistanceBetweenTwoCities(String startLocation, String endLocation) throws Exception {
		HashMap<String, Double> cityCoordinates = getCoordinatesOfCity(startLocation);
		Double latitudeStartLocation = cityCoordinates.get("latitude");
		Double longitudeStartLocation = cityCoordinates.get("longitude");

		if (latitudeStartLocation == null || longitudeStartLocation == null) {
			throw new InvalidCityException("Invalid start location");
		}

		cityCoordinates = getCoordinatesOfCity(endLocation);
		Double latitudeEndLocation = cityCoordinates.get("latitude");
		;
		Double longitudeEndLocation = cityCoordinates.get("longitude");

		if (latitudeEndLocation == null || longitudeEndLocation == null) {
			throw new InvalidCityException("Invalid end location");
		}

		System.out.println("[" + longitudeStartLocation + " , " + latitudeStartLocation + "],[" + longitudeEndLocation
				+ " , " + latitudeEndLocation + "]");
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
			throw new IOException("Failed to get coordinates: HTTP error code " + responseCode);
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
				System.out.println("-> Distance: " + distance + " km");
			}
			return distance;
		} finally {
			connection.disconnect();
		}
	}
}
