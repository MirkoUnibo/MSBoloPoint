package com.example.msbolopoint.service;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.dto.QuartieriDto;
import com.example.msbolopoint.dto.QuartieriNumberPointsDto;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class QuartieriService {
    @Autowired
    private QuartieriRepository repo;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    private final PointToPointDTOMapper mapper = new PointToPointDTOImpl();

    public List<QuartieriDto> findAll(){
        var x = repo.findAll();

        List<Object> sourceList = repo.getAllQuartieriWithNumberPoints();
        List<QuartieriDto> targetList =
                sourceList.stream
                                ()
                        .map(sourceObject -> {
                            QuartieriDto targetObject = new QuartieriDto();
                            targetObject.setId((UUID) ((Object[])sourceObject)[0]);
                            targetObject.setNumPoints((Long) ((Object[])sourceObject)[1]);
                            targetObject.setSuggestions((Long) ((Object[])sourceObject)[2]);
                            targetObject.setNomeQuart((String) ((Object[])sourceObject)[3]);
                            targetObject.setPerimetro(coordinateTransformation(((Object[])sourceObject)[4].toString()) );
                            return targetObject;
                        })
                        .toList();


        //System.out.println("polease");


//        listaQuartieriResponse.forEach(quartiere -> {
//            Long numPoints = numPointsMap.get(quartiere.getId());
//            if (numPoints != null) {
//                quartiere.setNumPoints(numPoints);
//            }
//        });

        return targetList;
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