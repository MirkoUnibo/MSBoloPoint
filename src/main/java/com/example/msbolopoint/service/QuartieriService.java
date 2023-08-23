package com.example.msbolopoint.service;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.dto.QuartieriDto;
import com.example.msbolopoint.mapper.PointToPointDTOImpl;
import com.example.msbolopoint.mapper.PointToPointDTOMapper;
import com.example.msbolopoint.model.PointOfInterest;
import com.example.msbolopoint.model.Quartieri;
import com.example.msbolopoint.repo.PointRepository;
import com.example.msbolopoint.repo.QuartieriRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class QuartieriService {
    @Autowired
    private QuartieriRepository repo;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    private final PointToPointDTOMapper mapper = new PointToPointDTOImpl();

    public List<QuartieriDto> findAll(){
        var x = repo.findAll();
        List<QuartieriDto> listaQuartieriResponse = new ArrayList<>();
        x.forEach(quartiere -> {
            QuartieriDto quartiereDto = new QuartieriDto();
            quartiereDto.setId(quartiere.getId());
            quartiereDto.setNomeQuart(quartiere.getNomequart());
            quartiereDto.setPerimetro(coordinateTransformation(quartiere.getPerimeter().toString()));
            listaQuartieriResponse.add(quartiereDto);
        });

        return listaQuartieriResponse;
    }

    private String coordinateTransformation(String polygonString) {

            //String polygonString = "POLYGON ((11.370620553995026 44.551664568816086, 11.370661970872328 44.551657518994226, 11.370702468276098 44.551685934820036))";

            String cleanPolygonString = polygonString.replaceAll("[A-Za-z()]", "").trim();
            String[] coordinatePairs = cleanPolygonString.split(",");

            StringBuilder result = new StringBuilder();
            result.append("[");

            for (String pair : coordinatePairs) {
                String[] coordinates = pair.trim().split(" ");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);

                result.append("[").append(x).append(",").append(y).append("], ");
            }

            result.setLength(result.length() - 2); // Remove the trailing comma and space
            result.append("]");

            return result.toString();
    }

}