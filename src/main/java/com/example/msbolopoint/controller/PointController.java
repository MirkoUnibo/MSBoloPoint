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
        System.out.println(idPoint);
        List poi = service.deletePoi(idPoint);
        if (poi == null) {
            throw new Exception("POI non cancellato");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/insert-poi")

    public ResponseEntity<PointResponseDTO> create(@RequestBody String jsonPoiInsert) throws Exception {
        System. out. println(jsonPoiInsert);
        PointResponseDTO poi = service.insertPoi(jsonPoiInsert);
        if (poi == null) {
            throw new Exception("POI non inserito");
        }
        return new ResponseEntity<>(poi, HttpStatus.CREATED);
    }

    @GetMapping(path = "/findAround")

    public ResponseEntity<List<PointResponseDTO>> findAround(
            @RequestParam  double lat,
            @RequestParam  double lon,
            @RequestParam double distanceM,
            @RequestParam int rank,
            @RequestParam String type) {
        var pointsAround = service.findNearest(lat, lon, distanceM, rank, type);
        if(pointsAround.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointsAround, HttpStatus.OK);
    }

    @GetMapping(path = "/findNearest")

    public ResponseEntity<PointResponseDTO> findnearest(
            @RequestParam  double lat,
            @RequestParam  double lon,
            @RequestParam int rank,
            @RequestParam String type
    ) {
        var pointsNearest = service.findAround(lat, lon, rank, type);
        if(pointsNearest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointsNearest, HttpStatus.OK);
    }

}
