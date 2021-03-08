package com.example.parking.dto.request;

public class ParkingSpot {
    private String parkingSpot;
    private Integer noOfParkingSpot;

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Integer getNoOfParkingSpot() {
        return noOfParkingSpot;
    }

    public void setNoOfParkingSpot(Integer noOfParkingSpot) {
        this.noOfParkingSpot = noOfParkingSpot;
    }
}
