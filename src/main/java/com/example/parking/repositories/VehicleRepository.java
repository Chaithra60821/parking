package com.example.parking.repositories;

import com.example.parking.constants.ParkingSpotType;
import com.example.parking.constants.PropertyName;
import com.example.parking.constants.VehicleType;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@Repository
public class VehicleRepository {

    @Autowired
    private DataSource dataSource;

    public static final String vehicletypeId = "select VehicleTypeId from VehicleType where VehicleTypeName = ?";

    String INSERT_VEHICLE = "IF NOT EXISTS (SELECT * FROM Vehicle WHERE VehicleRegNumber = ?)\n" +
            "    INSERT INTO Vehicle (VehicleRegNumber, VehicleTypeId, ParkingSpotId, IsRoyal, Count)\n" +
            "    VALUES (?, ?, ?, ?, 1)\n" +
            "ELSE\n" +
            "    UPDATE Vehicle\n" +
            "    SET Count = Count + 1\n" +
            "    WHERE VehicleRegNumber = ?";

    public Integer insertVehilce(String vehicleRegNo, Integer vechicleypeId, String parkingSpotId, Boolean isRoyal, Connection connection) throws ParkingException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VEHICLE, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, vehicleRegNo);
            preparedStatement.setString(2, vehicleRegNo);
            preparedStatement.setInt(3, vechicleypeId);
            preparedStatement.setString(4, parkingSpotId);
            preparedStatement.setBoolean(5, isRoyal);
            preparedStatement.setString(6, vehicleRegNo);
            Integer rs = preparedStatement.executeUpdate();
            if(rs != null) {
                return rs;
            } else {
                throw new ParkingException(ErrorCode.PARKING_CREATION.toString());
            }
        } catch (SQLException | ParkingException throwables) {
            throw  new ParkingException(ErrorCode.PARKING_CREATION.toString(), throwables);
        }
    }

    public Integer getVehicleId(VehicleType vehicleType) throws ParkingException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(vehicletypeId)) {
            preparedStatement.setString(1, vehicleType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }
            Integer parkingLotId = resultSet.getInt(1);
            return parkingLotId;
        } catch (SQLException throwables) {
            throw new ParkingException(ErrorCode.PARKING_SPOT_NOT_PRESENT.getMessage().replace("{var}", vehicleType.name()), throwables);
        }
    }
}
