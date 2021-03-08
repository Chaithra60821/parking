package com.example.parking.dto.responses;

public class ParkingResponse {
    private String vechileRegistrationNumber;
    private String spotId;
    private String FloorId;
    private String parkingLotName;

    public String getVechileRegistrationNumber() {
        return vechileRegistrationNumber;
    }

    public void setVechileRegistrationNumber(String vechileRegistrationNumber) {
        this.vechileRegistrationNumber = vechileRegistrationNumber;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getFloorId() {
        return FloorId;
    }

    public void setFloorId(String floorId) {
        FloorId = floorId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    @Override
    public String toString() {
        return "ParkingResponse{" +
                "vechileRegistrationNumber='" + vechileRegistrationNumber + '\'' +
                ", spotId='" + spotId + '\'' +
                ", FloorId='" + FloorId + '\'' +
                ", parkingLotName='" + parkingLotName + '\'' +
                '}';
    }
}
