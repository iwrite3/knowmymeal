package com.factbody.api.controller;


import com.factbody.api.model.NutritionFact;

import com.factbody.api.service.NutritionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class WebController {

    private final NutritionService nutritionService;

    public WebController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<NutritionFact> facts = nutritionService.getAllFacts();
        model.addAttribute("facts", facts);
        model.addAttribute("factCount", nutritionService.getFactCount());
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("foodName") String foodName, Model model) {
        // The food string comes from your HTML form input
        nutritionService.generateFact(foodName);
        return "redirect:/";
    }
}