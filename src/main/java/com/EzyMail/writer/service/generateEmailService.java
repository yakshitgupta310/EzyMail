package com.EzyMail.writer.service;

import com.EzyMail.writer.dto.Request;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class generateEmailService {

    @Autowired
    private RestTemplate restTemplate;

    private WebClient webClient;

    public generateEmailService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.ApiKey}")
    private String geminiApiKey;


    public String generateEmail(Request request){
        String prompt = buildPrompt(request);

        Map<String, Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(geminiApiUrl+geminiApiKey, HttpMethod.POST, entity, String.class);

        return extractResponseContent(response.getBody());
    }

    private String buildPrompt(Request request){
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email response for the following mail content. Please don't generate a subject for the response.");
        if(request.getTone()!=null && !request.getTone().isEmpty()){
            prompt.append("Use a ").append(request.getTone()).append(" tone.");
        }
        prompt.append("\n Original Mail Content: \n").append(request.getContent());
        return prompt.toString();
    }

    private String extractResponseContent(String response){
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0).path("content").path("parts").get(0).path("text").toString();
        } catch (Exception e) {
            return "Error processing the request " + e.getMessage();
        }
    }
}
