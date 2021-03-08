package com.example.parking.services.impl;

import com.example.parking.constants.ParkingSpotType;
import com.example.parking.constants.PropertyName;
import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.repositories.DataBaseRepository;
import com.example.parking.repositories.ParkingFloorRepository;
import com.example.parking.repositories.ParkingLotRepository;
import com.example.parking.repositories.ParkingSpotRepository;
import com.example.parking.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Admin service to create parkingLot
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingFloorRepository parkingFloorRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private DataBaseRepository dataBaseRepository;

    /**
     * creating a parking lot
     * @param createParkingLot
     * @return parkingLotId
     * @throws ParkingException
     * @throws SQLException
     */
    @Override
    public String createParkingLot(CreateParkingLot createParkingLot) throws ParkingException, SQLException {
        Connection connection = null;
        try{
            connection = dataBaseRepository.getDBConnection();
            String parkingLotId = parkingLotRepository.getParkingLotId(createParkingLot.getParkingLotName(), connection);
            if(parkingLotId == null) {
                LOG.error("Parking lot with the name {} is not present in the DB ", createParkingLot.getParkingLotName());
                throw new ParkingException(ErrorCode.PARKING_LOT_IS_NOT_PRESENT.getMessage());
            }
            String created_parkingLotId = parkingLotRepository.createParkingLot(createParkingLot, connection);
            if(created_parkingLotId == null) {
                LOG.error("Not able to create a parking lot for the parkingName {}", createParkingLot.getParkingLotName());
                throw new ParkingException(ErrorCode.PARKING_LOT_NOT_CREATED.getMessage().replace("{var}", createParkingLot.getParkingLotName()));
            }

            Boolean floorCreation = createParkingFloor(createParkingLot, created_parkingLotId, connection);
            if(!floorCreation)  {
                LOG.error("Not able to create a parking floor for the parkingName {}", createParkingLot.getParkingLotName());
                throw new ParkingException(ErrorCode.PARKING_LOT_NOT_CREATED.getMessage().replace("{var}", createParkingLot.getParkingLotName()));
            }

            connection.commit();
        } catch (Exception exception) {
            connection.rollback();
            throw new ParkingException(ErrorCode.PARKING_CREATION.getMessage(), exception);
        } finally {
            connection.close();
        }
        return createParkingLot.getParkingLotName();
    }

    /***
     * Creating parking floor
     * @param createParkingLot
     * @param parkingLotId
     * @param connection
     * @return
     * @throws ParkingException
     */
    private Boolean createParkingFloor(CreateParkingLot createParkingLot, String parkingLotId, Connection connection) throws ParkingException {
            //TODO : read a data from input like parking spot details and parkingfloor details
            for(int i=0; i<=createParkingLot.getNoParkingFloors(); i++) {
                Integer bike_parking_spot_id = parkingSpotRepository.getParkingSpotId(ParkingSpotType.BIKE);
                Integer car_parking_spot_id = parkingSpotRepository.getParkingSpotId(ParkingSpotType.CAR);
                String bikeSpotParkingFloor  = insertParkingFloor(i, parkingLotId, bike_parking_spot_id, PropertyName.NO_BIKE_SPOT, PropertyName.NO_BIKE_SPOT, connection);
                String carSpotParkingFloor  = insertParkingFloor(i, parkingLotId, car_parking_spot_id, PropertyName.NO_CAR_SPOT, PropertyName.NO_CAR_SPOT, connection);
                insertParkingSpots(bikeSpotParkingFloor, bike_parking_spot_id, connection);
                insertParkingSpots(carSpotParkingFloor, car_parking_spot_id, connection);
            }
        return true;
    }

    /**
     * parking spots are created in the below way (visually we can assume as below)
     *  1_1, 1_2, 1_3, 1_4, 1_5
     *  2_1, 2_2, 2_3, 2_4, 2_5
     *  3_1, 3_2, 3_3, 3_4, 3_5
     *  4_1, 4_2, 4_3, 4_4, 4_5
     *  5_1, 5_2, 5_3, 5_4, 5_5
     * @param parkingFloorId
     * @param parkingSpotId
     * @param connection
     * @throws ParkingException
     */
    private void insertParkingSpots(String parkingFloorId, Integer parkingSpotId, Connection connection) throws ParkingException {
        Integer noOfRows = PropertyName.NO_OF_ROWS;
        Integer noOfCols = PropertyName.NO_OF_COLS;
        Integer noOfLeves = PropertyName.NO_OF_LEVELS;
        int count = 0;
        for(int i=1; i<=noOfRows; i++) {
            for(int j=1; j<noOfCols; j++) {
                for(int k=0; k<noOfLeves; k++) {
                     parkingSpotRepository.createParkingSpot(parkingFloorId, i, j, k,parkingSpotId, connection);
                     count++;
                }
            }
        }
        //TODO : do some validation to match the No_spots if that doesnot fail the creation
    }

    private String insertParkingFloor(int i, String parkingLotId, Integer parking_spot_id, Integer noSpot, Integer noAvailableSpot, Connection connection) throws ParkingException {
        return parkingFloorRepository.createParkingFloor(i, parkingLotId, parking_spot_id, noSpot, noAvailableSpot, connection);
    }

}
