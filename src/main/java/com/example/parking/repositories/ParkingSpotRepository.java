package com.example.parking.repositories;

import com.example.parking.constants.ParkingSpotType;
import com.example.parking.constants.PropertyName;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.model.ParkingSpotParkingFloor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class ParkingSpotRepository {

    public static final Logger LOG = LoggerFactory.getLogger(ParkingSpotRepository.class);

    @Autowired
    private DataSource dataSource;

    public static final String parkingSpotId = "select ParkingSpotTypeId from ParkingSpotType where ParkingSpotTypeName = ?";
    public static final String PARKINGSPOT_INSERT = "INSERT INTO ParkingSpot (ParkingSpotId, ParkingSpotRowNumber, ParkingSpotColNumber, ParkingFloorId, " +
            "ParkingSpotTypeId,\n" +
            "LevelId, IsConverted, IsFree, IsActive, LockVersion, UpdatedAt)\n" +
            "VALUES(?,?,?,?,?,?,0,1,1,1, GETDATE());";

    //TODO : change NoAvailableParkingSpots -> availableParkingSpots
    //not sure this OUTPUT.ParkingSpot.ParkingSpotId will work in H2 database
    public static final String NORMAL_PARKING_SPOT = "UPDATE ParkingSpot\n" +
            "SET IsFree = true\n" +
            //"OUTPUT ParkingSpot.ParkingSpotId\n" +
            "where ParkingSpotId =\n" +
            "(select top 1 ParkingSpotId from ParkingSpot\n" +
            "INNER JOIN\n" +
            "(select  top 1 ParkingFloorId from ParkingFloor where\n" +
            "ParkingLotId = ? and IsActive = true and ParkingSpotTypeId = ? and NoAvailableParkingSpots>0\n" +
            "order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId\n" +
            "where IsFree=true and IsActive=true and IsConverted =false\n" +
            "ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC)";

    public static final String ELDER_PARKING_SPOT = "UPDATE ParkingSpot\n" +
            "SET IsFree = true\n" +
            //"OUTPUT ParkingSpot.ParkingSpotId\n" +
            "where ParkingSpotId =\n" +
            "(select top 1 ParkingSpotId from ParkingSpot\n" +
            "INNER JOIN\n" +
            "(select  top 1 ParkingFloorId from ParkingFloor where\n" +
            "ParkingLotId = 'parkinglot1' and IsActive = true and ParkingSpotTypeId = ? and NoAvailableParkingSpots>0\n" +
            "order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId\n" +
            "where IsFree=true and IsActive=true and IsConverted =false and LevelId = 0\n" +
            "ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC)";

    public static final String UN_PARK = "UPDATE ParkingSpot\n" +
            "SET IsFree = true\n" +
            "OUTPUT ParkingSpot.ParkingSpotId, ParkingSpot.ParkingFloorId\n" +
            "where ParkingSpotId = ?";

    public Integer getParkingSpotId(ParkingSpotType parkingSpotType) throws ParkingException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(parkingSpotId)) {
            preparedStatement.setString(1, parkingSpotType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }
            Integer parkingLotId = resultSet.getInt(1);
            return parkingLotId;
        } catch (SQLException throwables) {
            throw new ParkingException(ErrorCode.PARKING_SPOT_NOT_PRESENT.getMessage().replace("{var}", parkingSpotType.name()), throwables);
        }
    }

    public String createParkingSpot(String parkingFloorId, Integer row, Integer col, Integer level, Integer parking_spot_id, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(PARKINGSPOT_INSERT)){
            String parkingSpotId = UUID.randomUUID().toString();
            preparedStatement.setString(1, parkingSpotId);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, col);
            preparedStatement.setString(4, parkingFloorId);
            preparedStatement.setInt(5, parking_spot_id);
            preparedStatement.setInt(6, level);
            if(PropertyName.SUCCESS == preparedStatement.executeUpdate()) {
                LOG.info("Parking spot is {} created for the flooid {}", parking_spot_id, parkingFloorId);
                return parkingSpotId;
            } else {
                throw new ParkingException(ErrorCode.PARKING_CREATION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_CREATION.toString(), throwables);
        }
    }

    public ParkingSpotParkingFloor getNormalParkingSpot(String parkingLotId, Integer parkingSpotTypeId, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(NORMAL_PARKING_SPOT)){
            preparedStatement.setString(1, parkingLotId);
            preparedStatement.setInt(2, parkingSpotTypeId);
            //not recommnded few drivers may not support we have to change this the query to get the updated records
            ResultSetMetaData resultSet = preparedStatement.executeQuery().getMetaData();
            if(resultSet != null) {
                ParkingSpotParkingFloor parkingSpotParkingFloor = new ParkingSpotParkingFloor();
                parkingSpotParkingFloor.setParkingFloorId(resultSet.getColumnName(3));
                parkingSpotParkingFloor.setParkingSpotId(resultSet.getColumnName(1));
                return parkingSpotParkingFloor;
            } else {

                throw new ParkingException(ErrorCode.PARKING_NO_PARKING_SLOT.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_TRANSACTION.toString(), throwables);
        }
    }

    public ParkingSpotParkingFloor getElderParkingSpot(String parkingLotId, Integer parkingSpotTypeId, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(NORMAL_PARKING_SPOT)){
            preparedStatement.setString(1, parkingLotId);
            preparedStatement.setInt(2, parkingSpotTypeId);
            //not recommnded few drivers may not support we have to change this the query to get the updated records
            ResultSetMetaData resultSet = preparedStatement.executeQuery().getMetaData();
            if(resultSet != null) {
                ParkingSpotParkingFloor parkingSpotParkingFloor = new ParkingSpotParkingFloor();
                parkingSpotParkingFloor.setParkingFloorId(resultSet.getColumnName(2));
                parkingSpotParkingFloor.setParkingSpotId(resultSet.getColumnName(1));
                return parkingSpotParkingFloor;
            } else {
                throw new ParkingException(ErrorCode.PARKING_TRANSACTION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_TRANSACTION.toString(), throwables);
        }
    }

    public ParkingSpotParkingFloor unPark(String parkingSpotId, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UN_PARK)){
            preparedStatement.setString(1, parkingSpotId);
            //not recommnded few drivers may not support we have to change this the query to get the updated records
            ResultSetMetaData resultSet = preparedStatement.executeQuery().getMetaData();
            if(resultSet != null) {
                ParkingSpotParkingFloor parkingSpotParkingFloor = new ParkingSpotParkingFloor();
                parkingSpotParkingFloor.setParkingFloorId(resultSet.getColumnName(2));
                parkingSpotParkingFloor.setParkingSpotId(resultSet.getColumnName(1));
                return parkingSpotParkingFloor;
            } else {
                LOG.info("No parking lot is available");
                throw new ParkingException(ErrorCode.PARKING_TRANSACTION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_TRANSACTION.toString(), throwables);
        }
    }
}
