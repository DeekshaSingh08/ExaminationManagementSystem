package com.seating.examinationManagementSystem.controller;

import com.seating.examinationManagementSystem.config.JwtTokenUtil;
import com.seating.examinationManagementSystem.dto.JwtRequestDto;
import com.seating.examinationManagementSystem.dto.JwtResponseDto;
import com.seating.examinationManagementSystem.serviceImpl.CustomerUserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/examinationmanagementsystem")
public class JwtAuthenticationController {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = customerUserDetailService
                .loadUserByUsername(authenticationRequest.getUsername());
//        final String token = "Bearer " + jwtTokenUtil.generateToken(userDetails);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            logger.info("User authenticated...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.error("User disabled...");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials...");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
