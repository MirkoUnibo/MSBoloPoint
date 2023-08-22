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
    @Query(value="SELECT * from points where ST_DistanceSphere(geom, :p) < :distanceM and rank >= :rank and type = :type ORDER BY ST_DistanceSphere(geom, :p) LIMIT 1", nativeQuery = true)
    List<PointOfInterest> findNearWithinDistance(Point p, double distanceM, int rank, String type);

//    @Query(value="SELECT * from points WHERE ST_DISTANCE(geom, ST_setSRID(ST_MAKEPOINT(:lat, :lon),4326)) = (select min(ST_DISTANCE(geom, ST_setSRID(ST_MAKEPOINT(:lat, :lon),4326))) from points where rank >= :rank)", nativeQuery = true)
//    PointOfInterest findNearPoint(double lat, double lon);

    @Query(value="select * FROM points where rank >= :rank and type = :type ORDER BY ST_DistanceSphere(geom, :p) limit 1", nativeQuery = true)
    PointOfInterest findNearPoint(Point p, int rank, String type);

}
