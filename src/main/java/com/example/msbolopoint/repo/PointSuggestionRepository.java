package com.example.msbolopoint.repo;

import com.example.msbolopoint.model.PointSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PointSuggestionRepository extends JpaRepository<PointSuggestion, UUID> {
}
