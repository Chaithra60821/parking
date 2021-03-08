package com.example.parking.repositories;

import com.example.parking.constants.PropertyName;
import com.example.parking.constants.Status;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingRepository {

    @Autowired
    private DataSource dataSource;

    String INSERT_PARKING =
            "    INSERT INTO T_Parking (VehicleId, Status, UpdatedAt)\n" +
            "    VALUES (?, ?, GETDATE());";

    String UPDATE_PARKING_STATUS = "UPDATE T_Parking\n" +
            "SET Status = ? , UpdatedAt = GETDATE()\n" +
            "where  ParkingTransId =\n" +
            "(select top 1 ParkingTransId from T_Parking  \n" +
            "INNER JOIN\n" +
            "(select VehicleId from ParkingSpot where ParkingSpotId = '') as v on v.VehicleId = T_Parking.ParkingTransId\n" +
            "where T_Parking.STATUS = 'PARK'\n" +
            "ORDER BY T_Parking.UpdatedAt) ";

    public Integer insertParking(Integer vechicleypeId, Status status, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PARKING, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, vechicleypeId);
            preparedStatement.setString(2, status.name());
            Integer rs = preparedStatement.executeUpdate();
            if(rs != null) {
                return rs;
            } else {
                throw new ParkingException(ErrorCode.PARKING_TRANSACTION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_TRANSACTION.toString(), throwables);
        }
    }

    public boolean updateStatus(String parkingSpotId, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PARKING_STATUS)){
            preparedStatement.setString(1, parkingSpotId);
            if(PropertyName.SUCCESS ==  preparedStatement.executeUpdate()) {
                return true;
            } else {
                throw new ParkingException(ErrorCode.PARKING_UNPARK_EXCEPTION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_UNPARK_EXCEPTION.toString(), throwables);
        }
    }
}
