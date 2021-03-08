package com.example.parking.services;

import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ParkingException;
import java.sql.SQLException;

public interface AdminService extends AbstractService{
    String createParkingLot(CreateParkingLot createParkingLot) throws ParkingException, SQLException;
//    void addParkingFloor(String parkingLotId, ParkingFloor parkingFloor) throws ParkingException;
//    void addParkingSpot(String parkingLotId, String parkingFloorId, ParkingSpot parkingSpot) throws ParkingException;

}
