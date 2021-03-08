package com.example.parking.dto.request;

import java.util.List;

public class CreateParkingLot {
    private String parkingLotName;
    private Integer noParkingFloors;
    private List<ParkingFloor> parkingFloorList;

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public Integer getNoParkingFloors() {
        return noParkingFloors;
    }

    public void setNoParkingFloors(Integer noParkingFloors) {
        this.noParkingFloors = noParkingFloors;
    }

    public List<ParkingFloor> getParkingFloorList() {
        return parkingFloorList;
    }

    public void setParkingFloorList(List<ParkingFloor> parkingFloorList) {
        this.parkingFloorList = parkingFloorList;
    }
}
