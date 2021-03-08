package com.example.parking.dto.request;

import com.example.parking.constants.VehicleType;
import com.example.parking.model.Vehicle;

public class ParkReq {
    private String parkingLotName;
    private Vehicle vehicle;
    private boolean isReqForElder;

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isReqForElder() {
        return isReqForElder;
    }

    public void setReqForElder(boolean reqForElder) {
        isReqForElder = reqForElder;
    }
}
