package com.example.msbolopoint.repo;

import com.example.msbolopoint.dto.KMeansRequestDto;
import com.example.msbolopoint.dto.KMeansResponseDto;
import com.example.msbolopoint.model.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Integer> {
    @Query(value="SELECT ST_ClusterKMeans(posizione, 3) over() as cluster_id, user_position.id, user_position.posizione  FROM user_position",nativeQuery = true)
    List<Object> generateKMeans();
}
