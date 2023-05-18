package com.example.msbolopoint.mapper;

import com.example.msbolopoint.bean.Poi;
import com.example.msbolopoint.model.PointOfInterest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PoiMapper {
    PoiMapper INSTANCE = Mappers.getMapper(PoiMapper.class);
    Poi PointofInterestToPoi(PointOfInterest poi);
}
