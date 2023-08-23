package com.example.msbolopoint.controller;

import com.example.msbolopoint.dto.PointResponseDTO;
import com.example.msbolopoint.dto.QuartieriDto;
import com.example.msbolopoint.model.Quartieri;
import com.example.msbolopoint.service.PointService;
import com.example.msbolopoint.service.QuartieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("quartieri")
public class QuartieriController {
    @Autowired
    private QuartieriService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")

    public ResponseEntity<List<QuartieriDto>> getAllQuartieri() {
        var quartieriList = service.findAll();
        if(quartieriList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quartieriList, HttpStatus.OK);
    }


}
