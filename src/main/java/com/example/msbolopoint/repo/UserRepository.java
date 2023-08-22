package com.example.msbolopoint.repo;


import com.example.msbolopoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserNameOrEmail(String username, String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}
