package com.example.msbolopoint.repo;

import com.example.msbolopoint.bean.Poi;
import com.example.msbolopoint.model.PointOfInterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface



PointRepository extends JpaRepository<PointOfInterest, UUID> {
    
}
