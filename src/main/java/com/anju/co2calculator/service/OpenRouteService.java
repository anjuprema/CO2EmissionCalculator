package com.anju.co2calculator.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class OpenRouteService implements DistanceCalcuationService {
	private static final String apiKey = System.getenv("ORS_TOKEN");
    private static final String baseURL = "https://api.openrouteservice.org/";
    private URL requestURL;
    private HttpURLConnection connection;
    
    public double getDistanceBetweenTwoCities(String startLocation, String endLocation) {
    	try {
            HashMap<String, Double> cityCoordinates = getCoordinatesOfCity(startLocation);                       
            Double lat1 = cityCoordinates.get("latitude");
            Double lon1 = cityCoordinates.get("longitude");
            
            cityCoordinates = getCoordinatesOfCity(endLocation);
            Double lat2 = cityCoordinates.get("latitude");;
            Double lon2 = cityCoordinates.get("longitude");
            
            System.out.println(lat1+" , "+lon1);
            System.out.println(lat2+ " , "+ lon2);
            requestURL = new URL(baseURL + "v2/matrix/driving-car");
            // JSON request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("locations", new JSONArray(Arrays.asList(
                    new JSONArray(Arrays.asList(lon1, lat1)),
                    new JSONArray(Arrays.asList(lon2, lat2))
            )));
            requestBody.put("metrics", new JSONArray(Arrays.asList("distance", "duration")));
            requestBody.put("units", "km");

            connection = (HttpURLConnection) requestURL.openConnection();
            // Set up the connection
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send request body
            try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                writer.writeBytes(requestBody.toString());
                writer.flush();
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
                JSONArray distances = jsonResponse.getJSONArray("distances");
                JSONArray durations = jsonResponse.getJSONArray("durations");

                // Extract results
                double distance = distances.getJSONArray(0).getDouble(1);
                double duration = durations.getJSONArray(0).getDouble(1) / 60; // Convert seconds to minutes

                // Output results
                System.out.println("🛣 Distance: " + distance + " km");
                System.out.println("⏳ Estimated Time: " + duration + " minutes");
                return distance;
            }

        } catch (Exception e) {
            System.err.println("⚠️ Error fetching distance/time: " + e.getMessage());
        } finally {
        	 connection.disconnect();
        }
		return 0;
    }

	private HashMap<String, Double> getCoordinatesOfCity(String cityName) {
		try {
            requestURL = new URL(baseURL + "geocode/search?api_key="+ apiKey +"&text="+ URLEncoder.encode(cityName, StandardCharsets.UTF_8));
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
       	 connection.disconnect();
       }
		return null;
	}

}
