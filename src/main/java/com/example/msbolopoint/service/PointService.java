package com.example.msbolopoint.service;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.mapper.PointToPointDTOImpl;
import com.example.msbolopoint.mapper.PointToPointDTOMapper;
import com.example.msbolopoint.model.PointOfInterest;
import com.example.msbolopoint.model.UserPosition;
import com.example.msbolopoint.repo.PointRepository;
import com.example.msbolopoint.repo.UserPositionRepository;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PointService {
    @Autowired
    private PointRepository repo;
    @Autowired
    private UserPositionRepository userPositionRepository;
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

    public List deletePoi(UUID idPoi) throws JSONException {
        repo.deleteById(idPoi);
        return findAll();
    }
    public PointResponseDTO insertPoi(String jsonPoi) throws JSONException {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JtsModule());
        JSONObject jsonStr = new JSONObject (jsonPoi);
        jsonStr = jsonStr.getJSONObject("jsonPoint");
        //jsonStr = jsonStr["jsonPoiInsert"];
        GeometryFactory gf = new GeometryFactory();


        PointOfInterest newPoi = new PointOfInterest();
        newPoi.setId(UUID.randomUUID());
        return setPoi(jsonStr, gf, newPoi);
    }

    public PointResponseDTO updatePoi(UUID idPoi, String jsonPoi) throws Exception {

        Optional<PointOfInterest> pointOfInterest = repo.findById(idPoi);
        if(pointOfInterest.isEmpty()){
            throw new Exception("POI non esiste");
        }

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JtsModule());
        JSONObject jsonStr = new JSONObject (jsonPoi);
        GeometryFactory gf = new GeometryFactory();


        PointOfInterest newPoi = new PointOfInterest();
        newPoi.setId(idPoi);
        return setPoi(jsonStr, gf, newPoi);
    }

    private PointResponseDTO setPoi(JSONObject jsonStr, GeometryFactory gf, PointOfInterest newPoi) {
        newPoi.setName(jsonStr.getString("name"));
        newPoi.setRank(jsonStr.getInt("rank"));
        newPoi.setType(jsonStr.getString("type"));
        double x = jsonStr.getJSONArray("geom").getDouble(1);
        double y = jsonStr.getJSONArray("geom").getDouble(0);
        Point point = gf.createPoint(new Coordinate(x, y));
        newPoi.setGeom(point);
        PointOfInterest pointOfInterestSaved = repo.save(newPoi);
        return mapper.toDto(pointOfInterestSaved);
    }

    public List<UUID> findNearest(double lat, double lon, double distanceM, int rank, String type, int idUser){
        Point p = factory.createPoint(new Coordinate(lon, lat));
        UserPosition userPosition = new UserPosition();
        userPosition.setIdUser(idUser);
        userPosition.setPosizione(p);
        userPosition.setId(UUID.randomUUID());
        userPosition.setDataPosizione(Timestamp.from(Instant.now()));
        userPositionRepository.save(userPosition);
        return repo.findNearWithinDistance(p, distanceM, rank, type);
    }

    public PointResponseDTO findAround(double lat, double lon, int rank, String type, int idUser){
        Point p = factory.createPoint(new Coordinate(lon, lat));
        UserPosition userPosition = new UserPosition();
        userPosition.setIdUser(idUser);
        userPosition.setPosizione(p);
        userPosition.setId(UUID.randomUUID());
        userPosition.setDataPosizione(Timestamp.from(Instant.now()));
        userPositionRepository.save(userPosition);
        var z = repo.findNearPoint(p, rank, type);
        return mapper.toDto(z);
    }
}
