package com.factbody.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "body_facts")
public class BodyFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fact;

    @Column(name = "source_api")
    private String sourceApi;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public BodyFact() {
        this.createdAt = LocalDateTime.now();
    }

    public BodyFact(String fact, String sourceApi) {
        this.fact = fact;
        this.sourceApi = sourceApi;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFact() { return fact; }
    public void setFact(String fact) { this.fact = fact; }

    public String getSourceApi() { return sourceApi; }
    public void setSourceApi(String sourceApi) { this.sourceApi = sourceApi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
