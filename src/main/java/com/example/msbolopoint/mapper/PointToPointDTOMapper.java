package com.example.msbolopoint.mapper;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.model.PointOfInterest;

import java.util.List;
public interface PointToPointDTOMapper {
    PointResponseDTO toDto(PointOfInterest entity);

    List<PointResponseDTO> toDto(List<PointOfInterest> entity);

}