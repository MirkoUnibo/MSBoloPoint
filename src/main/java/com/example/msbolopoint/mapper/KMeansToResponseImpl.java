package com.example.msbolopoint.mapper;

import com.example.msbolopoint.dto.KMeansRequestDto;
import com.example.msbolopoint.dto.KMeansResponseDto;
import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.dto.QuartieriDto;
import com.example.msbolopoint.model.PointOfInterest;
import org.geolatte.geom.Point;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class KMeansToResponseImpl implements KMeansToResponseMapper{
    @Override
    public KMeansResponseDto toDto(Object entity) {

        KMeansResponseDto kMeansResponseDto = new KMeansResponseDto();
        kMeansResponseDto.setClusterId((Integer) ((Object[])entity)[0]);
        Point point = (Point) ((Object[]) entity)[2];
        var x = point.getPosition().getCoordinate(0);
        var y = point.getPosition().getCoordinate(1);
        Coordinate coordinate = new Coordinate(x,y);
        kMeansResponseDto.setCoordinate(coordinate);
        kMeansResponseDto.setIdPosition((UUID) ((Object[])entity)[1]);


        return kMeansResponseDto;

    }

    @Override
    public List<KMeansResponseDto> toDto(List<Object> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
