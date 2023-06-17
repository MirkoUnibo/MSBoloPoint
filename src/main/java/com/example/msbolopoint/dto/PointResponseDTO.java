package com.example.msbolopoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PointResponseDTO {

    private UUID id;

    private String name;

    private String type;
    private int rank;

    private Coordinate coordinate;
}
