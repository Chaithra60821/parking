package com.example.parking.controller;

import com.example.parking.dto.request.ParkReq;
import com.example.parking.dto.request.UnParkReq;
import com.example.parking.dto.responses.ParkingResponse;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.services.impl.ParkingServiceImpl;
import com.example.parking.utils.ResponseEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parking")
public class ParkingController {

    @Autowired
    ParkingServiceImpl parkingService;

    @Autowired
    ResponseEntityUtil responseEntityUtil;

    public static Logger LOG = LoggerFactory.getLogger(ParkingController.class);

    /**
     * getParking spot to park the vehicle
     * @param parkingLotReq
     * @return parkingSpot details
     */
    @RequestMapping(path = "/park", method = RequestMethod.POST)
    public ResponseEntity getParkingSlot(@RequestBody ParkReq parkingLotReq) {
        try {
            LOG.info("PARK ==> Park request for the parkingLot {} -- Start", parkingLotReq.getParkingLotName());
            ParkingResponse park = parkingService.park(parkingLotReq);
            LOG.info("PARK ==> Park request for the parkingLot {} -- End", parkingLotReq.getParkingLotName());
            return responseEntityUtil.buildParkResponse(park);
        } catch (Exception e) {
            ParkingException parkingException = new ParkingException(ErrorCode.PARKING_CREATION.getMessage(), e);
            return responseEntityUtil.buildErrorResponse(parkingException, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * unpark the parking spot
     * @param unParkReq
     * @return success/failure
     */
    @RequestMapping(path = "/unpark", method = RequestMethod.POST)
    public ResponseEntity unPark(@RequestBody UnParkReq unParkReq) {
        try {
            LOG.info("PARK ==> Park request for the parkingLot {} -- Start", unParkReq.getParkingSpotName());
            parkingService.unpark(unParkReq);
            LOG.info("PARK ==> Park request for the parkingLot {} -- End", unParkReq.getParkingSpotName());
            return new ResponseEntity<>("message : unparking the parking spot", HttpStatus.OK);
        } catch (Exception e) {
            ParkingException parkingException = new ParkingException(ErrorCode.PARKING_UNPARK_EXCEPTION.getMessage(), e);
            return responseEntityUtil.buildErrorResponse(parkingException, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(path = "/availaSlots", method = RequestMethod.POST)
//    public ResponseEntity unPark(String parkingLotName) {
//        try {
//            LOG.info("PARK ==> Park request for the parkingLot {} -- Start", unParkReq.getParkingSpotName());
//            parkingService.unpark(unParkReq);
//            LOG.info("PARK ==> Park request for the parkingLot {} -- End", unParkReq.getParkingSpotName());
//            return new ResponseEntity<>("message : unparking the parking spot", HttpStatus.OK);
//        } catch (Exception e) {
//            ParkingException parkingException = new ParkingException(ErrorCode.PARKING_UNPARK_EXCEPTION.getMessage(), e);
//            return responseEntityUtil.buildErrorResponse(parkingException, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
