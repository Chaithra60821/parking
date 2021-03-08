package com.example.parking.dto.responses;


import com.example.parking.constants.ParkingSpotType;

import java.util.Map;

public class ParkingFloorResponse {
    private String parkingFloorId;
    private Map<ParkingSpotType, Integer> parkingSpotTypeIntegerMap;

    public Map<ParkingSpotType, Integer> getParkingSpotTypeIntegerMap() {
        return parkingSpotTypeIntegerMap;
    }

    public void setParkingSpotTypeIntegerMap(Map<ParkingSpotType, Integer> parkingSpotTypeIntegerMap) {
        this.parkingSpotTypeIntegerMap = parkingSpotTypeIntegerMap;
    }

    public String getParkingFloorId() {
        return parkingFloorId;
    }

    public void setParkingFloorId(String parkingFloorId) {
        this.parkingFloorId = parkingFloorId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("ParkingFloorID = " +parkingFloorId);
        builder.append("\n");
        builder.append("    "+"Floor details");
        parkingSpotTypeIntegerMap.forEach((pt, size) -> {
            builder.append("\n");
            builder.append("        " + pt +"=" + size);

        });
        return builder.toString();
    }
}
