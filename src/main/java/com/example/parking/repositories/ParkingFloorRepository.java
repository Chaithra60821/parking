package com.example.parking.repositories;

import com.example.parking.constants.PropertyName;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class ParkingFloorRepository {
    public static final String PARKINGFLOOR_INSERT = "INSERT INTO ParkingFloor " +
            "(ParkingFloorId, ParkingFloorNumber,ParkingLotId,ParkingSpotTypeId, NoParkingSpots, NoAvailableParkingSpots, isActive, LockVersion, UpdatedAt)\n" +
            " VALUES (?,?, ?, ?, ?, ?, 1, 1, GETDATE())";

    public static final String PARKINGFLOOR_GET = "Select ParkingFloorId from ParkingFloor where " +
            "ParkingFloorNumber=? and ParkingLotId=?";

    public static final String PARKING_AVAIL_COUNT_INC = "UPDATE ParkingFloor\n" +
            "    SET NoAvailableParkingSpots = NoAvailableParkingSpots - 1\n" +
            "    WHERE ParkingFloorId = ?";

    public static final String PARKING_AVAIL_COUNT_DEC = "UPDATE ParkingFloor\n" +
            "    SET NoAvailableParkingSpots = NoAvailableParkingSpots - 1\n" +
            "    WHERE ParkingFloorId = ?";

    //TODO : Change this query as a bulk insert
    public String createParkingFloor(int floor, String parkingLotId, Integer parking_spot_id, Integer no_spots,
                                   Integer no_spots_available, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(PARKINGFLOOR_INSERT)){
            String parkingFloorId = UUID.randomUUID().toString();
            preparedStatement.setString(1, parkingFloorId);
          preparedStatement.setInt(2, floor);
          preparedStatement.setString(3, parkingLotId);
          preparedStatement.setInt(4, parking_spot_id);
          preparedStatement.setInt(5, no_spots);
          preparedStatement.setInt(6, no_spots_available);
            if(PropertyName.SUCCESS == preparedStatement.executeUpdate()) {
                return parkingFloorId;
            } else {
                throw new ParkingException(ErrorCode.PARKING_CREATION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_CREATION.toString(), throwables);
        }
    }

    public boolean updateAvailableParkingSlots(String parkingFloorId, Boolean isInc, Connection connection) throws ParkingException {
        String query;
        if(isInc){
            query = PARKING_AVAIL_COUNT_INC;
        } else {
            query = PARKING_AVAIL_COUNT_DEC;
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, parkingFloorId);
            if(PropertyName.SUCCESS == preparedStatement.executeUpdate()) {
                return true;
            } else {
                throw new ParkingException(ErrorCode.PARKING_CREATION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_CREATION.toString(), throwables);
        }
    }
}
