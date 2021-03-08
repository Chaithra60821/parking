package com.example.parking.dto.responses;

import org.springframework.http.HttpStatus;

public class ParkParkingResponseDTO {
    private String message;
    private String parkingSpotId;
    private String parkingFloorId;
    private String parkingRegNumber;
    private HttpStatus httpStatus;

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

    public String getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(String parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public String getParkingFloorId() {
        return parkingFloorId;
    }

    public void setParkingFloorId(String parkingFloorId) {
        this.parkingFloorId = parkingFloorId;
    }

    public String getParkingRegNumber() {
        return parkingRegNumber;
    }

    public void setParkingRegNumber(String parkingRegNumber) {
        this.parkingRegNumber = parkingRegNumber;
    }
}
