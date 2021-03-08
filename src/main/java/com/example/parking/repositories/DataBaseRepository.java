package com.example.parking.repositories;

import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class DataBaseRepository {

    @Autowired
    private DataSource dataSource;

    public Connection getDBConnection() throws ParkingException {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException throwables) {
            throw new ParkingException(ErrorCode.PARKING_DB_CON_ERROR.getMessage(), throwables);
        }
    }
}
