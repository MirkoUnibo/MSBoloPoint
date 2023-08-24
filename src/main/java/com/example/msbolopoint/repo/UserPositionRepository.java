package com.example.msbolopoint.repo;

import com.example.msbolopoint.model.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Integer> {
}
