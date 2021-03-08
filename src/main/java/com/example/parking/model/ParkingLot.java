package com.example.parking.model;

import java.sql.Timestamp;

public class ParkingLot {
    private Integer parkingLotId;
    private String parkingLotName;
    private Integer noOfFloors;
    private Integer strategyId;
    private boolean isActive;
    private Integer lockVersion;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }


    public Integer getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(Integer noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }
}
