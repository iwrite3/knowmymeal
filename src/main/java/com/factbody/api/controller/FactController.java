package com.factbody.api.controller;

import com.factbody.api.model.ApiResponse;
import com.factbody.api.model.BodyFact;
import com.factbody.api.service.FactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facts")
@Tag(name = "Body Facts", description = "Endpoints for generating and retrieving human body facts")
public class FactController {

    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate a new body fact using DeepSeek")
    public ResponseEntity<ApiResponse<BodyFact>> generate() {
        BodyFact fact = factService.generateFact();
        return ResponseEntity.ok(ApiResponse.ok("Fact generated", fact));
    }

    @GetMapping
    @Operation(summary = "Get all stored body facts")
    public ResponseEntity<ApiResponse<List<BodyFact>>> getAllFacts() {
        List<BodyFact> facts = factService.getAllFacts();
        return ResponseEntity.ok(ApiResponse.ok("Retrieved " + facts.size() + " facts", facts));
    }

    @GetMapping("/latest")
    @Operation(summary = "Get the most recently generated fact")
    public ResponseEntity<ApiResponse<BodyFact>> getLatestFact() {
        return factService.getLatestFact()
                .map(f -> ResponseEntity.ok(ApiResponse.ok("Latest fact", f)))
                .orElse(ResponseEntity.ok(ApiResponse.error("No facts generated yet")));
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of stored facts")
    public ResponseEntity<ApiResponse<Long>> getCount() {
        return ResponseEntity.ok(ApiResponse.ok("Total facts", factService.getFactCount()));
    }
}
