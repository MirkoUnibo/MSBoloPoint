package com.example.msbolopoint.dto;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
public class KMeansRequestDto {
    private int clusterId;
    private UUID idPosition;
    private Point posizione;
}