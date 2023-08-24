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

    @Query(value = "SELECT\n" +
            "    \n" +
            "q.id\n" +
            ",\n" +
            "    COUNT(pp) as num_Points,\n" +
            "    CAST(sum(pp.suggestion) as BIGINT) as suggestions,\n" +
            "    q.nomequart as nome_quart,\n" +
            "    q.perimeter as perimetro\n" +
            "FROM\n" +
            "    Quartieri q\n" +
            "LEFT JOIN\n" +
            "    (points p inner join point_suggestion ps on \n" +
            "p.id\n" +
            " = ps.idpoint) as pp\n" +
            "    ON ST_Contains(CAST(CAST(q.perimeter AS geography) as geometry), CAST(CAST(pp.geom AS geography) as geometry))\n" +
            "GROUP BY\n" +
            "    \n" +
            "q.id\n" +
            ",\n" +
            "    q.nomequart,\n" +
            "    q.perimeter; ", nativeQuery = true)
    List<Object> getAllQuartieriWithNumberPoints();

}