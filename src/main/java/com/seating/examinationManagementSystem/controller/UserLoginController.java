package com.seating.examinationManagementSystem.controller;

import com.seating.examinationManagementSystem.config.JwtTokenUtil;

import com.seating.examinationManagementSystem.dto.UserLoginRequestDto;
import com.seating.examinationManagementSystem.dto.UserLoginResponseDto;
import com.seating.examinationManagementSystem.serviceImpl.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/examinationmanagementsystem")
public class UserLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = customerUserDetailService
                .loadUserByUsername(loginRequest.getUsername());

        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList().get(0);

        String jwtToken = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserLoginResponseDto(jwtToken, userRole));
    }
}
