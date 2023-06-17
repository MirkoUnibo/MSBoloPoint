package com.example.msbolopoint.mapper;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PointToPointDTOImpl implements PointToPointDTOMapper{
    @Override
    public PointResponseDTO toDto(PointOfInterest entity) {
        PointResponseDTO pointResponseDTO = new PointResponseDTO();
		pointResponseDTO.setId(entity.getId());
		pointResponseDTO.setCoordinate(entity.getGeom().getCoordinate());
		pointResponseDTO.setType(entity.getType());
		pointResponseDTO.setRank(entity.getRank());
		pointResponseDTO.setName(entity.getName());
        return pointResponseDTO;
    }

    @Override
    public List<PointResponseDTO> toDto(List<PointOfInterest> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
