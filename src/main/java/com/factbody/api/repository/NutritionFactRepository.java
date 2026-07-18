package com.factbody.api.repository;

import com.factbody.api.model.NutritionFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionFactRepository extends JpaRepository<NutritionFact, Long> {

    /**
     * Retrieves the most recently saved nutrition fact based on the creation timestamp.
     * This supports the "/latest" endpoint in your controller.
     */
    Optional<NutritionFact> findTopByOrderByCreatedAtDesc();
}