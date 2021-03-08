package com.example.parking.dto.responses;

import org.springframework.http.HttpStatus;

public class CreateParkingResponseDTO {
    private String message;
    private String name;
    private HttpStatus httpStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
