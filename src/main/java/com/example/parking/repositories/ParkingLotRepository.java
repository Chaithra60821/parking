package com.example.parking.repositories;

import com.example.parking.constants.PropertyName;
import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class ParkingLotRepository {
    public static final Logger LOG = LoggerFactory.getLogger(ParkingLotRepository.class);
     public static final String parkingLotName = "select ParkingLotId from ParkingLot where ParkingLotName = ?";

    public static final String parkingLot_insert = "INSERT INTO ParkingLot " +
            "(ParkingLotName,  NoOfFloors, isActive, LockVersion, UpdatedAt)\n" +
            "VALUES (?, ?, 1, 1, GETDATE())\n";

    public static final String parkingFloor_insert = "INSERT INTO ParkingLot " +
            "(ParkingLotId, ParkingLotName,  NoOfFloors, isActive, LockVersion, UpdatedAt)\n" +
            "VALUES (?,?, ?, 1, 1, GETDATE())\n";

     @Autowired
     private DataSource dataSource;

     public String getParkingLotId(String parkingLotName, Connection connection) throws ParkingException {
         try(PreparedStatement preparedStatement = connection.prepareStatement(parkingLotName)) {
             preparedStatement.setString(1, parkingLotName);
             ResultSet resultSet = preparedStatement.executeQuery();
             if(!resultSet.next()) {
                 return null;
             }
             String parkingLotId = resultSet.getString(1);
             return parkingLotId;
         } catch (SQLException throwables) {
             throw new ParkingException(ErrorCode.PARKING_DB_GETPARKINGLOT.getMessage(), throwables);
         }
     }

    public String createParkingLot(CreateParkingLot createParkingLot, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(parkingFloor_insert)) {
            String parkingLotId = UUID.randomUUID().toString();
            preparedStatement.setString(1, createParkingLot.getParkingLotName());
            preparedStatement.setInt(2, createParkingLot.getNoParkingFloors());
            if(PropertyName.SUCCESS == preparedStatement.executeUpdate()) {
                return parkingLotId;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throw new ParkingException(ErrorCode.PARKING_DB_PUTPARKINGLOT.getMessage(), throwables);
        }
    }
}
