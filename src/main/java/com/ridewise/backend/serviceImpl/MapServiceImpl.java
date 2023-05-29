package com.ridewise.backend.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridewise.backend.exception.InternalServerErrorException;
import com.ridewise.backend.model.Address;
import com.ridewise.backend.model.LocationDetails;
import com.ridewise.backend.model.LocationSuggestion;
import com.ridewise.backend.service.MapService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private static final String SEARCH_API_URL = "https://nominatim.openstreetmap.org/search?q=%s&format=json&limit=%s";
    private static final String REVERSE_API_URL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s";

    public List<LocationSuggestion> getLocationSuggestions(String search, String limit) {
        try {
            String urlString = String.format(SEARCH_API_URL, search, limit);
            return performSearchRequest(urlString);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to get location suggestions");
        }
    }

    public LocationDetails getLocationDetailsByLotAndLat(String lon, String lat) {
        try {
            String urlString = String.format(REVERSE_API_URL, lat, lon);
            return performReverseGeocodingRequest(urlString);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to perform reverse geocoding");
        }
    }

    private List<LocationSuggestion> performSearchRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            String response = responseBuilder.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<LocationSuggestion> locationSuggestionList = new ArrayList<>();
            for (JsonNode node : rootNode) {
                String displayName = node.get("display_name").asText();
                String latitude = node.get("lat").asText();
                String longitude = node.get("lon").asText();
                locationSuggestionList.add(new LocationSuggestion(displayName, latitude, longitude));
            }

            return locationSuggestionList;
        }  catch (IOException e) {
            throw new InternalServerErrorException("Failed to get location suggestions");
        }finally {
            connection.disconnect();
        }
    }

    private LocationDetails performReverseGeocodingRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            String response = responseBuilder.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode addressNode = rootNode.get("address");
            String displayName = rootNode.get("display_name").asText();

            Address address = Address.builder()
                    .houseNumber(addressNode.get("house_number").asText())
                    .road(addressNode.get("road").asText())
                    .neighbourhood(addressNode.get("neighbourhood").asText())
                    .quarter(addressNode.get("quarter").asText())
                    .suburb(addressNode.get("suburb").asText())
                    .city(addressNode.get("city").asText())
                    .state(addressNode.get("state").asText())
                    .iso3166_2_lvl4(addressNode.get("ISO3166-2-lvl4").asText())
                    .postcode(addressNode.get("postcode").asText())
                    .postcode(addressNode.get("postcode").asText())
                    .country(addressNode.get("country").asText())
                    .countryCode(addressNode.get("country_code").asText())
                    .build();
            return new LocationDetails(displayName, address);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to perform reverse geocoding");
        } finally {
            connection.disconnect();
        }
    }
}
