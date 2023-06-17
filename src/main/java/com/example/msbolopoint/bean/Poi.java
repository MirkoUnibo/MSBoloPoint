package com.example.msbolopoint.bean;

import lombok.Data;

import java.util.UUID;

@Data
public class Poi {
    private UUID id;
    private String name;
    public OurGeom geom;
    private int tipologia;
    private int rank;
}
