package com.EzyMail.writer.service;

import com.EzyMail.writer.dto.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class rephraseTextService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.ApiKey}")
    private String geminiApiKey;

    public String rephraseText(Request request){
        //form request
        String prompt = buildPrompt(request);
        Map<String, Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );
        //make a call to gen api
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(geminiApiUrl+geminiApiKey, HttpMethod.POST, entity, String.class);
        //extract text from response
        //String data = extractRephrasedText(response.getBody());

        return extractRephrasedText(response.getBody());
        //return response.getBody();
    }

    private String extractRephrasedText(String body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(body);
            String response = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            return response;
        } catch (Exception e) {
            return "Error processing the request " + e.getMessage();
        }
    }

    private String buildPrompt(Request request) {
        StringBuilder builder = new StringBuilder();
        builder.append("Rephrase the following content. Keep it concise and easy to understand.");
        builder.append("\nHere is the content - ").append(request.getContent());
        return builder.toString();
    }
}

