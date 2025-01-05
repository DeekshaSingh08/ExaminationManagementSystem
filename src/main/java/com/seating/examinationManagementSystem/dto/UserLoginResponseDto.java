package com.seating.examinationManagementSystem.dto;

public class UserLoginResponseDto {

    private String token;
    private String userRole;

    public UserLoginResponseDto(String token, String userRole) {
        this.token = token;
        this.userRole = userRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}

