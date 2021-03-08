package com.example.parking.dto.request;

import java.util.List;

public class ParkingFloor {
    private Integer parkingFloor;
    private List<ParkingSpot> parkingSpot;

    public Integer getParkingFloor() {
        return parkingFloor;
    }

    public void setParkingFloor(Integer parkingFloor) {
        this.parkingFloor = parkingFloor;
    }

    public List<ParkingSpot> getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(List<ParkingSpot> parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
