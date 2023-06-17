package com.example.msbolopoint.repo;

import com.example.msbolopoint.model.PointOfInterest;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface



PointRepository extends JpaRepository<PointOfInterest, UUID> {
@Query(value="SELECT * from points where ST_DistanceSphere(geom, :p) < :distanceM", nativeQuery = true)
    List<PointOfInterest> findNearWithinDistance(Point p, double distanceM);
}
