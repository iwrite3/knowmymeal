package com.factbody.api.service;

import com.factbody.api.model.DeepSeekRequest;
import com.factbody.api.model.DeepSeekResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DeepSeekService {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekService.class);

    private final RestClient restClient;

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    public DeepSeekService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getRandomBodyFact() {
        String prompt = "Give me one fascinating, lesser-known random fact about the human body. "
                + "Keep it to 1-2 sentences. Do not number it. Just state the fact directly.";

        DeepSeekRequest request = new DeepSeekRequest(prompt);

        try {
            DeepSeekResponse response = restClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(DeepSeekResponse.class);

            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent().trim();
            }
            return "No fact returned from DeepSeek.";
        } catch (Exception e) {
            log.error("DeepSeek API error: {}", e.getMessage());
            return "Failed to fetch fact from DeepSeek: " + e.getMessage();
        }
    }
}
