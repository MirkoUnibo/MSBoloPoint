package com.example.msbolopoint.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class Poi {
    private UUID id;
    private String name;
    public OurGeom geom;
}
