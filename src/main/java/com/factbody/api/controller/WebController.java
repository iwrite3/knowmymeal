package com.factbody.api.controller;

import com.factbody.api.model.BodyFact;
import com.factbody.api.service.FactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {

    private final FactService factService;

    public WebController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<BodyFact> facts = factService.getAllFacts();
        model.addAttribute("facts", facts);
        model.addAttribute("factCount", factService.getFactCount());
        return "index";
    }

    @PostMapping("/generate")
    public String generate(Model model) {
        factService.generateFact();
        return "redirect:/";
    }
}
