package com.ridewise.backend.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridewise.backend.exception.InternalServerErrorException;
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
    @Override
    public List<LocationSuggestion> getLocationSuggestions(String search, String limit) {
        try {
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + search + "&format=json&limit=" + limit;
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

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
            connection.disconnect();

            return locationSuggestionList;
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to get location suggestions");
        }
    }
}
