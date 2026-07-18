package com.factbody.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nutrition_facts")
public class NutritionFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "nutrition_details", nullable = false, columnDefinition = "TEXT")
    private String nutritionDetails;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "source_api")
    private String sourceApi;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public NutritionFact() {
        this.createdAt = LocalDateTime.now();
    }

    public NutritionFact(String foodName, String nutritionDetails, String recommendation, String sourceApi) {
        this.foodName = foodName;
        this.nutritionDetails = nutritionDetails;
        this.recommendation = recommendation;
        this.sourceApi = sourceApi;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getNutritionDetails() { return nutritionDetails; }
    public void setNutritionDetails(String nutritionDetails) { this.nutritionDetails = nutritionDetails; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getSourceApi() { return sourceApi; }
    public void setSourceApi(String sourceApi) { this.sourceApi = sourceApi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}