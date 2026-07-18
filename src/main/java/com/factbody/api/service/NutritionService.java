package com.factbody.api.service;

import com.factbody.api.model.NutritionFact;
import com.factbody.api.repository.NutritionFactRepository; // Make sure you renamed your repository
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionService {

    private final NutritionFactRepository nutritionFactRepository;
    private final DeepSeekService deepSeekService; // Assuming you kept this name for your AI calls

    public NutritionService(NutritionFactRepository nutritionFactRepository, DeepSeekService deepSeekService) {
        this.nutritionFactRepository = nutritionFactRepository;
        this.deepSeekService = deepSeekService;
    }

    /**
     * Takes the food input from the user, gets nutritional info from the AI, and saves it.
     */
    public NutritionFact generateFact(String foodName) {
        String prompt = "Provide the nutritional values and a short recommendation on whether it is a healthy meal for: " + foodName;

        String aiResponse = deepSeekService.callApi(prompt);

        // We now pass the entire AI response into the details field, and leave the recommendation field blank.
        NutritionFact fact = new NutritionFact(foodName, aiResponse, "", "DeepSeek");
        return nutritionFactRepository.save(fact);
    }

    public List<NutritionFact> getAllFacts() {
        return nutritionFactRepository.findAll();
    }

    public Optional<NutritionFact> getLatestFact() {
        // Assuming your repository has a method to find the top one ordered by creation date descending
        return nutritionFactRepository.findTopByOrderByCreatedAtDesc();
    }

    public long getFactCount() {
        return nutritionFactRepository.count();
    }
}