package com.example.msbolopoint.service;

import com.example.msbolopoint.bean.OurGeom;
import com.example.msbolopoint.bean.Poi;
import com.example.msbolopoint.model.PointOfInterest;

import com.example.msbolopoint.repo.PointRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PointService {
    @Autowired
    private PointRepository repo;
    private static WKTReader wktReader = new WKTReader();



    public List<Poi> findAll(Pageable page){
        var pointOfInterest = repo.findAll(page);
        List<Poi> poiList = new ArrayList<>();
        pointOfInterest.forEach(pointOfInterest1 -> poiList.add(setPoi(pointOfInterest1)));
        return poiList;
    }
    public Poi findById(UUID id){
        Optional<PointOfInterest> pointOfInterest = repo.findById(id);
        return pointOfInterest.map(this::setPoi).orElse(null);
    }

    public Poi insertPoi(String jsonPoi) throws JSONException {
        JSONObject jsonStr = new JSONObject (jsonPoi);
        PointOfInterest newPoi = new PointOfInterest();
        newPoi.setId(UUID.randomUUID());
        newPoi.setName(jsonStr.getString("name"));
        newPoi.setGeom(parseLocation((double) jsonStr.getJSONArray("geom").get(1), (double) jsonStr.getJSONArray("geom").get(0)));
        PointOfInterest pointOfInterest = repo.save(newPoi);
        return setPoi(pointOfInterest);
    }

    public Poi deletePoi(UUID idPoi) throws JSONException {
        repo.deleteById(idPoi);
        return findById(idPoi);
    }

    private Poi setPoi(PointOfInterest pointOfInterest) {
        Poi poi = new Poi();
        poi.setGeom(new OurGeom(pointOfInterest.getGeom().getInteriorPoint().getCoordinate().x, pointOfInterest.getGeom().getInteriorPoint().getCoordinate().y));
        poi.setName(pointOfInterest.getName());
        poi.setId(pointOfInterest.getId());
        return poi;
    }

    private static Geometry wktToGeometry(String wellKnownText) {
        Geometry geometry = null;

        try {
            geometry = wktReader.read(wellKnownText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("###geometry :"+geometry);
        return geometry;
    }
    public static Geometry parseLocation(double x,double y) {
        return PointService.wktToGeometry(String.format("POINT (%s %s)",x,y));
    }

}
