package com.example.msbolopoint.service;


import com.example.msbolopoint.dto.KMeansResponseDto;
import com.example.msbolopoint.mapper.KMeansToResponseImpl;
import com.example.msbolopoint.mapper.KMeansToResponseMapper;
import com.example.msbolopoint.repo.UserPositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPositionService {
    @Autowired
    private UserPositionRepository repo;
    private final KMeansToResponseMapper mapper = new KMeansToResponseImpl();

    public List<KMeansResponseDto> generateKMeans(){
        var x = repo.generateKMeans();

        return mapper.toDto(x);
    }


}
