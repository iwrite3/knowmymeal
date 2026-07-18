package com.factbody.api.controller;

import com.factbody.api.model.ApiResponse;
import com.factbody.api.model.NutritionFact;
import com.factbody.api.service.NutritionService; // 1. Add this import!

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facts") // Consider changing this to "/api/nutrition" in the future!
@Tag(name = "Nutrition Facts", description = "Endpoints for analyzing food and retrieving nutrition facts") // Updated description
public class FactController {

    // 2. Declare the service
    private final NutritionService nutritionService;

    // 3. Inject it via the constructor
    public FactController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @PostMapping("/generate") // Fixed typo here (was "/generte")
    @Operation(summary = "Generate a nutrition fact for a specific food using DeepSeek")
    public ResponseEntity<ApiResponse<NutritionFact>> generate(@RequestParam("foodName") String foodName) {
        NutritionFact fact = nutritionService.generateFact(foodName);
        return ResponseEntity.ok(ApiResponse.ok("Nutrition fact generated", fact));
    }

    @GetMapping
    @Operation(summary = "Get all stored nutrition facts")
    public ResponseEntity<ApiResponse<List<NutritionFact>>> getAllFacts() {
        List<NutritionFact> facts = nutritionService.getAllFacts();
        return ResponseEntity.ok(ApiResponse.ok("Retrieved " + facts.size() + " nutrition facts", facts));
    }

    @GetMapping("/latest")
    @Operation(summary = "Get the most recently generated nutrition fact")
    public ResponseEntity<ApiResponse<NutritionFact>> getLatestFact() {
        return nutritionService.getLatestFact()
                .map(f -> ResponseEntity.ok(ApiResponse.ok("Latest nutrition fact", f)))
                .orElse(ResponseEntity.ok(ApiResponse.error("No nutrition facts generated yet")));
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of stored nutrition facts")
    public ResponseEntity<ApiResponse<Long>> getCount() {
        return ResponseEntity.ok(ApiResponse.ok("Total nutrition facts", nutritionService.getFactCount()));
    }
}