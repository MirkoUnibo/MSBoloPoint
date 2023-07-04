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

    public List<PointResponseDTO> findAround(double lat, double lon, double distanceM, int rank, String type){
        Point p = factory.createPoint(new Coordinate(lon, lat));
        return mapper.toDto(repo.findNearWithinDistance(p, distanceM, rank, type));
    }

    public PointResponseDTO findNearest(double lat, double lon, int rank, String type){
        Point p = factory.createPoint(new Coordinate(lon, lat));
        var z = repo.findNearPoint(p, rank, type);
        return mapper.toDto(z);
    }
}
