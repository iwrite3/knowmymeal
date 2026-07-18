package com.factbody.api.service;

import com.factbody.api.model.DeepSeekRequest;
import com.factbody.api.model.DeepSeekResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url:https://api.deepseek.com/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public DeepSeekService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Calls the DeepSeek API with a specific prompt about the user's food.
     */
    public String callApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // System prompt instructs DeepSeek on HOW to behave.
        // User prompt contains the actual food to analyze.
        DeepSeekRequest request = new DeepSeekRequest(
                "You are an expert nutritionist. Analyze the food provided by the user. " +
                        "Provide the response in two clear sections: " +
                        "1. Nutritional Values (Calories, Protein, Carbs,Fibres ,Fats) " +
                        "2. Recommendation (Is this a good/healthy meal and why?)",
                prompt
        );

        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<DeepSeekResponse> response = restTemplate.postForEntity(
                    apiUrl,
                    entity,
                    DeepSeekResponse.class
            );

            if (response.getBody() != null && response.getBody().getChoices() != null && !response.getBody().getChoices().isEmpty()) {
                return response.getBody().getChoices().get(0).getMessage().getContent();
            } else {
                return "Could not generate nutrition facts at this time.";
            }
        } catch (Exception e) {
            // Log the error in a real application
            return "Error communicating with AI service: " + e.getMessage();
        }
    }
}