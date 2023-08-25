package com.example.msbolopoint.controller;


import com.example.msbolopoint.dto.KMeansResponseDto;
import com.example.msbolopoint.service.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kmeans")
public class KMeansController {
    @Autowired
    private UserPositionService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")

    public ResponseEntity<List<KMeansResponseDto>> getAllPoint() {
        var kmeansList = service.generateKMeans();
        if(kmeansList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kmeansList, HttpStatus.OK);
    }
}
