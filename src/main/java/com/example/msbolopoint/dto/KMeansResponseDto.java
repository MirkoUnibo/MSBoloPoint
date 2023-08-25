package com.example.msbolopoint.dto;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.util.UUID;

@Data
public class KMeansResponseDto {
    private int clusterId;
    private UUID idPosition;
    private Coordinate coordinate;
}
