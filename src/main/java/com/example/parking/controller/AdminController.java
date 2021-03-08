package com.example.parking.controller;

import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ErrorCode;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.services.impl.AdminServiceImpl;
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
public class AdminController {

    private final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private ResponseEntityUtil responseEntityUtil;

    /**
     * Creating a parking lot
     */
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity createParkingLot(@RequestBody CreateParkingLot createParkingLot) {
        try {
            LOG.info("CREATE => Create parking lot req for the parking lot {} -- start ", createParkingLot.getParkingLotName());
            String parking_name = adminServiceImpl.createParkingLot(createParkingLot);
            LOG.info("CREATE => Create parking lot req for the parking lot {} -- end ", createParkingLot.getParkingLotName());
            return responseEntityUtil.buildCreateParkingLotResponse(parking_name);
        } catch (Exception e) {
            ParkingException parkingException = new ParkingException(ErrorCode.PARKING_CREATION.getMessage(), e);
            return responseEntityUtil.buildErrorResponse(parkingException, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
