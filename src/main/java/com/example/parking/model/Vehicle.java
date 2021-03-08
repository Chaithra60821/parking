package com.example.parking.model;

import com.example.parking.constants.VehicleType;

public class Vehicle {
    private String registrationNumber;
    private VehicleType vehicleType;
    private boolean isRoyalVehicle;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isRoyalVehicle() {
        return isRoyalVehicle;
    }

    public void setRoyalVehicle(boolean royalVehicle) {
        isRoyalVehicle = royalVehicle;
    }
}
