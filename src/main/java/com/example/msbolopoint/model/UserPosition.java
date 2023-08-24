package com.example.msbolopoint.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name="user_position")
public class UserPosition {
    @Id
    private UUID id;
    private Integer idUser;
    private Point posizione;
    private Timestamp dataPosizione;
}
