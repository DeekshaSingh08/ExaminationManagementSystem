package com.seating.examinationManagementSystem.dto;

import java.io.Serializable;

/**
 * JwtResponseDto class of API for jwt
 *
 * @author deeksha.singh
 */

public class JwtResponseDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public JwtResponseDto(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
