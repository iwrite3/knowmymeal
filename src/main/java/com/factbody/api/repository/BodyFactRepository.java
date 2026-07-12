package com.factbody.api.repository;

import com.factbody.api.model.BodyFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyFactRepository extends JpaRepository<BodyFact, Long> {

    Optional<BodyFact> findFirstByOrderByIdDesc();
}
