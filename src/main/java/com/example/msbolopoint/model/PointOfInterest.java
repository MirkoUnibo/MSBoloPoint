package com.example.msbolopoint.model;

import com.example.msbolopoint.bean.OurGeom;
import com.vividsolutions.jts.geom.Coordinate;
import lombok.*;
import org.locationtech.jts.geom.Geometry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

import java.awt.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "points")
public class PointOfInterest {

    @Id
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name = "geom")
    public Geometry geom;

    @Column(name="type")
    private String type;



}
