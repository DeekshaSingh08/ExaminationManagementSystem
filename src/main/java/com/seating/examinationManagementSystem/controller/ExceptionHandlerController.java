package com.seating.examinationManagementSystem.controller;


import com.seating.examinationManagementSystem.dto.ErrorResponseDto;
import com.seating.examinationManagementSystem.exception.BadRequestException;
import com.seating.examinationManagementSystem.exception.NotAcceptableException;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestControllerAdvice
@RequestMapping("/api/examinationmanagementsystem")
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ErrorResponseDto> handleNotAcceptableException(NotAcceptableException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
