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
public class QuartieriDto {

    private UUID id;

    private String nomeQuart;

    private String perimetro;

    private Long numPoints;

    private Long suggestions;
}
