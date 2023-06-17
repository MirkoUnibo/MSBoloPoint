package com.example.msbolopoint.service;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.mapper.PointToPointDTOImpl;
import com.example.msbolopoint.mapper.PointToPointDTOMapper;
import com.example.msbolopoint.model.PointOfInterest;
import com.example.msbolopoint.repo.PointRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PointService {
    @Autowired
    private PointRepository repo;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    private final PointToPointDTOMapper mapper = new PointToPointDTOImpl();

    public List<PointResponseDTO> findAll(){
        var x = repo.findAll();
        return mapper.toDto(x);
    }

    public PointResponseDTO findById(UUID id){
        Optional<PointOfInterest> pointOfInterest = repo.findById(id);
        return pointOfInterest.map(mapper::toDto).orElse(null);
    }

        public PointResponseDTO deletePoi(UUID idPoi) throws JSONException {
        repo.deleteById(idPoi);
        return findById(idPoi);
    }
    public PointResponseDTO insertPoi(String jsonPoi) throws JSONException {

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JtsModule());
        JSONObject jsonStr = new JSONObject (jsonPoi);
        GeometryFactory gf = new GeometryFactory();


        PointOfInterest newPoi = new PointOfInterest();
        newPoi.setId(UUID.randomUUID());
        newPoi.setName(jsonStr.getString("name"));
        newPoi.setRank(jsonStr.getInt("rank"));
        newPoi.setType(jsonStr.getString("type"));
        double x = jsonStr.getJSONArray("geom").getDouble(0);
        double y = jsonStr.getJSONArray("geom").getDouble(1);
        Point point = gf.createPoint(new Coordinate(x, y));
        newPoi.setGeom(point);
        PointOfInterest pointOfInterestSaved = repo.save(newPoi);
        return mapper.toDto(pointOfInterestSaved);
    }

//    public List<PointResponseDTO> findNearestPoint( double x, double y){
//        var z = mapper.toDto(repo.findNearestPoint(y,x));
//        return  null;
//    }

    public List<PointResponseDTO> findAround(double lat, double lon, double distanceM){
        Point p = factory.createPoint(new Coordinate(lon, lat));
        return mapper.toDto(repo.findNearWithinDistance(p, distanceM));
    }

//

//

//
//    private Poi setPoi(PointOfInterest pointOfInterest) {
//        Poi poi = new Poi();
//        poi.setGeom(new OurGeom(pointOfInterest.getGeom().getInteriorPoint().getCoordinate().x, pointOfInterest.getGeom().getInteriorPoint().getCoordinate().y));
//        poi.setName(pointOfInterest.getName());
//        poi.setId(pointOfInterest.getId());
//        return poi;
//    }
//
//    private static Geometry wktToGeometry(String wellKnownText) {
//        Geometry geometry = null;
//
//        try {
//            geometry = wktReader.read(wellKnownText);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("###geometry :"+geometry);
//        return geometry;
//    }
//    public static Geometry parseLocation(double x,double y) {
//        return PointService.wktToGeometry(String.format("POINT (%s %s)",x,y));
//    }

}
