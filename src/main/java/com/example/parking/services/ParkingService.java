package com.example.parking.services;

import com.example.parking.dto.request.ParkReq;
import com.example.parking.dto.request.UnParkReq;
import com.example.parking.dto.responses.ParkingResponse;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.model.Vehicle;

import java.sql.SQLException;

public interface ParkingService {

    public ParkingResponse park(ParkReq parkingLotReq) throws ParkingException, SQLException;

    public boolean unpark(UnParkReq unParkReq) throws ParkingException, SQLException;
}
