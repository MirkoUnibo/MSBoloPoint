package com.example.msbolopoint.repo;

import com.example.msbolopoint.dto.QuartieriDto;
import com.example.msbolopoint.dto.QuartieriNumberPointsDto;
import com.example.msbolopoint.model.PointOfInterest;
import com.example.msbolopoint.model.Quartieri;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface



QuartieriRepository extends JpaRepository<Quartieri, UUID> {

    @Query(value = "SELECT q.id, COUNT(p) " +
            "FROM Quartieri q, Points p " +
            "WHERE ST_Contains(CAST(CAST(q.perimeter AS geography) as geometry), CAST(CAST(p.geom AS geography) as geometry)) " +
            "GROUP BY q.id", nativeQuery = true)
    List<Object> getAllQuartieriWithNumberPoints();
}