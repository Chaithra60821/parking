package com.example.parking.utils;


import com.example.parking.constants.ParkingSpotType;
import com.example.parking.constants.VehicleType;

public class ParkingVechileTypeParkingSpotHelper {

    public static ParkingSpotType getParkingSpotForTheVehicle(VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR: return ParkingSpotType.CAR;
            case BIKE: return ParkingSpotType.BIKE;
            default : return null;
        }
    }
}
