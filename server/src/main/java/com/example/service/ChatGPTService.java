package com.example.service;

import com.example.controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.TextStyle;
import java.util.*;


import io.github.cdimascio.dotenv.Dotenv;
@Service
public class ChatGPTService {

    private final Dotenv dotenv = Dotenv.load();
    public String getApiKey() {
        return dotenv.get("GEMINI_API_KEY");
    }
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final String apiKey = getApiKey();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    // Constructor
    public ChatGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getChatGPTResponse(String input) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Error: API key is missing. Set GEMINI_API_KEY in environment variables.";
        }

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request Body
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        List<Map<String, Object>> parts = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayDateString = today.format(formatter);

        String todayDayName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        String fullPrompt = "I need the date in DD/MM/YEAR format and only the date as a single string without anything else for the following sentence. The output must only include the date in given format. And consider from today's date which is " + todayDateString + " and today is " + todayDayName + ". " + input + ".";
        logger.info("Sent string"+fullPrompt);
        parts.add(Collections.singletonMap("text", fullPrompt));
        content.put("parts", parts);
        request.put("contents", Collections.singletonList(content));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());

                if (jsonResponse.has("candidates")) {
                    JsonNode textNode = jsonResponse.path("candidates").get(0).path("content").path("parts").get(0).path("text");

                    if (textNode.isTextual()) {
                        String parsedText = textNode.asText().trim();
                        logger.info("Byeeeeeeeeeeee: {}", parsedText);
                        // Attempt to parse the response as a date
                        try {
//                            LocalDate parsedDate = LocalDate.parse(parsedText);
                            return parsedText; // Return in YYYY-MM-DD format
                        } catch (DateTimeParseException e) {
                            return "Error: Unable to parse date from response.";
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();

        }
        return "Error processing request.";
    }
}

