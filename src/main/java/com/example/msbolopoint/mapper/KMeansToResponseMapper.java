package com.example.msbolopoint.mapper;

import com.example.msbolopoint.dto.KMeansRequestDto;
import com.example.msbolopoint.dto.KMeansResponseDto;
import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.model.PointOfInterest;

import java.util.List;

public interface KMeansToResponseMapper {
    KMeansResponseDto toDto(Object entity);

    List<KMeansResponseDto> toDto(List<Object> entity);
}
