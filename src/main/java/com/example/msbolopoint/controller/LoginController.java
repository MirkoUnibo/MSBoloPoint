package com.example.msbolopoint.controller;

import com.example.msbolopoint.dto.LoginDto;
import com.example.msbolopoint.dto.LoginResponseDto;
import com.example.msbolopoint.dto.SignUpDto;
import com.example.msbolopoint.model.Role;
import com.example.msbolopoint.model.User;
import com.example.msbolopoint.repo.RoleRepository;
import com.example.msbolopoint.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = null;
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<String> strings = new ArrayList<>();
            authentication.getAuthorities().forEach(grantedAuthority -> strings.add(grantedAuthority.getAuthority()));
            loginResponseDto.setRuolo(strings.get(0));
            loginResponseDto.setMessaggio("User login successfully!...");
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        }catch (Exception e){
            loginResponseDto.setRuolo(null);
            loginResponseDto.setMessaggio("User login unsuccessful!...");
            return new ResponseEntity<>(loginResponseDto, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        // checking for username exists in a database
        if(userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }
        // checking for email exists in a database
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }
        // creating user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        String role = signUpDto.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        Role roles = roleRepository.findByName(role).get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
