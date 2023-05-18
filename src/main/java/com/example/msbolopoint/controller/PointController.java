package com.example.msbolopoint.controller;

import com.example.msbolopoint.bean.Poi;
import com.example.msbolopoint.mapper.PoiMapper;
import com.example.msbolopoint.model.PointOfInterest;
import com.example.msbolopoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("point")
public class PointController {
    @Autowired
    private PointService service;
    @Autowired
    PoiMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<Poi>> getAllPoint(Pageable pageable){
        var poi = service.findAll(pageable);
        return new ResponseEntity<>(poi, HttpStatus.CREATED);
    }

    @GetMapping("/{idPoint}")
    public Poi getPointById(@PathVariable("idPoint") UUID idPoint){
        return service.findById(idPoint);
    }

    @PostMapping(path = "/insert-poi")
    public ResponseEntity<Poi> create(@RequestBody String jsonPoi) throws Exception {
        Poi poi = service.insertPoi(jsonPoi);
        if (poi == null) {
            throw new Exception("POI non inserito");
        } else {
            return new ResponseEntity<>(poi, HttpStatus.CREATED);
        }
    }

    @PostMapping(path = "/delete-poi/{idPoint}")
    public ResponseEntity<Poi> delete(@PathVariable("idPoint") UUID idPoint) throws Exception {
        Poi poi = service.deletePoi(idPoint);
        if (poi == null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new Exception("POI non cancellato");
        }
    }

//    @RequestMapping(value = "/location/{id}", method = RequestMethod.PUT)
//    public ResponseEntity putLocation(@RequestHeader(value = AUTHORIZATION) String userId,
//                                      @PathVariable("id") Long id,
//                                      @RequestBody Feature feature) {
//
//        if (!locationService.exists(userId, id)) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//        locationService.updateLocation(userId, id, feature);
//        return new ResponseEntity(HttpStatus.OK);
//    }

}
