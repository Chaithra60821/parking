package com.example.parking.dto.request;

public class UnParkReq {
    private String parkingSpotName;
    private String parkingLotName;
    private String isRoylaVehicle;

    public String getParkingSpotName() {
        return parkingSpotName;
    }

    public void setParkingSpotName(String parkingSpotName) {
        this.parkingSpotName = parkingSpotName;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }
}
