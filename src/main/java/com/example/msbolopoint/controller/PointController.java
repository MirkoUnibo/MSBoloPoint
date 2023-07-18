package com.example.msbolopoint.controller;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("point")
public class PointController {
    @Autowired
    private PointService service;

    @GetMapping("/all")
    public ResponseEntity<List<PointResponseDTO>> getAllPoint() {
        var poiList = service.findAll();
        if(poiList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(poiList, HttpStatus.OK);
    }

    @GetMapping("/{idPoint}")
    public ResponseEntity<PointResponseDTO> getPointById(@PathVariable("idPoint") UUID idPoint) {
        PointResponseDTO point = service.findById(idPoint);
        if(point == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(point, HttpStatus.OK);
    }

    @PostMapping(path = "/delete-poi/{idPoint}")
    public ResponseEntity<?> delete(@PathVariable("idPoint") UUID idPoint) throws Exception {
        PointResponseDTO poi = service.deletePoi(idPoint);
        if (poi == null) {
            throw new Exception("POI non cancellato");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/insert-poi")
    public ResponseEntity<PointResponseDTO> create(@RequestBody String jsonPoiInsert) throws Exception {
        PointResponseDTO poi = service.insertPoi(jsonPoiInsert);
        if (poi == null) {
            throw new Exception("POI non inserito");
        }
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    @PutMapping(path = "/update-poi/{idPoint}")
    public ResponseEntity<PointResponseDTO> update(@PathVariable("idPoint")UUID idPoint, @RequestBody String jsonPoiInsert) throws Exception {
        PointResponseDTO poi = service.updatePoi(idPoint, jsonPoiInsert);
        if (poi == null) {
            throw new Exception("POI non inserito");
        }
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    @GetMapping("/findAround")
    public ResponseEntity<List<PointResponseDTO>> findAround(
            @RequestParam  double lat,
            @RequestParam  double lon,
            @RequestParam double distanceM,
            @RequestParam int rank,
            @RequestParam String type) {
        var pointsAround = service.findAround(lat, lon, distanceM, rank, type);
        if(pointsAround.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointsAround, HttpStatus.OK);
    }

    @GetMapping("/findNearest")
    public ResponseEntity<PointResponseDTO> findnearest(
            @RequestParam  double lat,
            @RequestParam  double lon,
            @RequestParam int rank,
            @RequestParam String type
    ) {
        var pointsNearest = service.findNearest(lat, lon, rank, type);
        if(pointsNearest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointsNearest, HttpStatus.OK);
    }

}
