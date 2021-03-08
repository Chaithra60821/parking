package com.example.parking.services.impl;

import com.example.parking.constants.Status;
import com.example.parking.dto.request.ParkReq;
import com.example.parking.dto.request.UnParkReq;
import com.example.parking.dto.responses.ParkingResponse;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.model.ParkingSpotParkingFloor;
import com.example.parking.repositories.DataBaseRepository;
import com.example.parking.repositories.ParkingFloorRepository;
import com.example.parking.repositories.ParkingLotRepository;
import com.example.parking.repositories.ParkingRepository;
import com.example.parking.repositories.ParkingSpotRepository;
import com.example.parking.repositories.VehicleRepository;
import com.example.parking.services.ParkingService;
import com.example.parking.utils.ParkingVechileTypeParkingSpotHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class ParkingServiceImpl implements ParkingService {

    public static Logger LOG = LoggerFactory.getLogger(ParkingServiceImpl.class);
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private DataBaseRepository dataBaseRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingFloorRepository parkingFloorRepository;

    //for a debugging pupose we can keep the single request tracking ID in the logs and exceptions also

    /**
     * Get the spot to park the vehicle
     * 1.Get the spot near to the exit
     * 2.Incremnt the count in the vehicel
     * 3.make a entry in parking spot
     * @param parkingLotReq
     * @return ParkingResponse
     * @throws ParkingException
     * @throws SQLException
     */
    public ParkingResponse park(ParkReq parkingLotReq) throws ParkingException, SQLException {
        Connection connection = dataBaseRepository.getDBConnection();
        //TODO: few validation we could do here
        // if vehicle is already in parked state we can reach thought it is rare to happen kind of double check
        try {
            String parkingLotId = parkingLotRepository.getParkingLotId(parkingLotReq.getParkingLotName(), connection);
            //instead of this i can also do a innerJoin in the query only for a particular spotTypeName
            Integer parkingSpotTypeId = parkingSpotRepository.getParkingSpotId(ParkingVechileTypeParkingSpotHelper.getParkingSpotForTheVehicle(parkingLotReq.getVehicle().getVehicleType()));
            ParkingSpotParkingFloor parkingSpotParkingFloor;
            if(!parkingLotReq.isReqForElder() && !parkingLotReq.getVehicle().isRoyalVehicle()) {
                parkingSpotParkingFloor = parkingSpotRepository.getNormalParkingSpot(parkingLotId, parkingSpotTypeId, connection);
            } else {
                    parkingSpotParkingFloor = parkingSpotRepository.getElderParkingSpot(parkingLotId, parkingSpotTypeId, connection);;
            }
            //TODO : yet to implement royal parking
            LOG.info("Got the parking spot {} " + parkingSpotParkingFloor.getParkingSpotId());
            parkingFloorRepository.updateAvailableParkingSlots(parkingSpotParkingFloor.getParkingFloorId(), false, connection);
            Integer vehicleId = vehicleRepository.insertVehilce(parkingLotReq.getVehicle().getRegistrationNumber(), vehicleRepository.getVehicleId(parkingLotReq.getVehicle().getVehicleType()), parkingSpotParkingFloor.getParkingFloorId(), false, connection);
            Integer parking_trans = parkingRepository.insertParking(vehicleId, Status.PARK,connection);
            ParkingResponse parkingResponse = new ParkingResponse();
            parkingResponse.setVechileRegistrationNumber(parkingLotReq.getVehicle().getRegistrationNumber());
            parkingResponse.setFloorId(parkingSpotParkingFloor.getParkingFloorId());
            parkingResponse.setSpotId(parkingSpotParkingFloor.getParkingSpotId());
            parkingResponse.setParkingLotName(parkingLotReq.getParkingLotName());
            LOG.info("Parking spot details {} ", parkingResponse.toString());
            connection.commit();
            return parkingResponse;
        } catch (Exception e) {
            connection.rollback();
            LOG.error("rooling back the transaction");
            throw new ParkingException(ErrorCode.PARKING_PARK_EXCEPTION.toString(), e);
        } finally {
            connection.close();
        }
    }

    /**
     * unpark the spot
     *  1. make the spot as free
     *  2. increment the avaulablespot count
     *  3. update the status of the paking_transaction
     * @param unParkReq
     * @return SUCCESS/FAILURE (boolean)
     * @throws ParkingException
     * @throws SQLException
     */
    public boolean unpark(UnParkReq unParkReq) throws ParkingException, SQLException {
        Connection connection = dataBaseRepository.getDBConnection();
        connection.setAutoCommit(false);
        try {
            ParkingSpotParkingFloor parkingSpotParkingFloor = parkingSpotRepository.unPark(unParkReq.getParkingLotName(), connection);
            parkingFloorRepository.updateAvailableParkingSlots(parkingSpotParkingFloor.getParkingFloorId(), false, connection);
            parkingRepository.updateStatus(parkingSpotParkingFloor.getParkingSpotId(), connection);
            LOG.info("ParkingSpot is free for the parking spot {} ", unParkReq.getParkingSpotName());
            connection.commit();
            return true;
        }catch (Exception e) {
            connection.rollback();
            LOG.error("rooling back the transaction");
            throw new ParkingException(ErrorCode.PARKING_UNPARK_EXCEPTION.toString(), e);
        } finally {
            connection.close();
        }
    }
}
