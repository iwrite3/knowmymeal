package com.factbody.api.service;

import com.factbody.api.model.BodyFact;
import com.factbody.api.repository.BodyFactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FactService {

    private static final Logger log = LoggerFactory.getLogger(FactService.class);

    private final BodyFactRepository repository;
    private final DeepSeekService deepSeekService;

    public FactService(BodyFactRepository repository,
                       DeepSeekService deepSeekService) {
        this.repository = repository;
        this.deepSeekService = deepSeekService;
    }

    public BodyFact generateFact() {
        log.info("Generating fact for user at time {}", LocalDateTime.now());
        String factText = deepSeekService.getRandomBodyFact();
        BodyFact fact = new BodyFact(factText, "DeepSeek");
        log.info("Completed generating fact for user at time {}", LocalDateTime.now());
        return repository.save(fact);
    }

    public List<BodyFact> getAllFacts() {
        return repository.findAll();
    }

    public Optional<BodyFact> getLatestFact() {
        return repository.findFirstByOrderByIdDesc();
    }

    public long getFactCount() {
        return repository.count();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearDatabaseDaily() {
        log.info("Clearing in-memory database...");
        repository.deleteAll();
        log.info("Database cleared successfully at {}", LocalDateTime.now());
    }
}
