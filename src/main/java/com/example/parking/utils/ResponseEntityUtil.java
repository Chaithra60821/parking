package com.example.parking.utils;

import com.example.parking.dto.responses.ParkingResponse;
import com.example.parking.dto.responses.CreateParkingResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ResponseEntityUtil {
    private static final Logger log = LoggerFactory.getLogger(ResponseEntityUtil.class);

    public ResponseEntity<CreateParkingResponseDTO> buildCreateParkingLotResponse(String name) {
        CreateParkingResponseDTO createParkingLotDTO = new CreateParkingResponseDTO();
        if (Objects.nonNull(name)) {
            createParkingLotDTO.setName(name);
            createParkingLotDTO.setHttpStatus(HttpStatus.OK);
            createParkingLotDTO.setMessage("ParkingLot Created Successfully.");
        } else {
            createParkingLotDTO.setMessage("Unable to create a ParkingLot");
            createParkingLotDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createParkingLotDTO, createParkingLotDTO.getHttpStatus());
    }

    public ResponseEntity<CreateParkingResponseDTO> buildParkResponse(ParkingResponse parkingResponse) {
        CreateParkingResponseDTO createParkingLotDTO = new CreateParkingResponseDTO();
        if (Objects.nonNull(parkingResponse)) {
            createParkingLotDTO.setName("");
            createParkingLotDTO.setHttpStatus(HttpStatus.OK);
            createParkingLotDTO.setMessage("ParkSpot is allocated");
        } else {
            createParkingLotDTO.setMessage("Unable to create a ParkingLot");
            createParkingLotDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createParkingLotDTO, createParkingLotDTO.getHttpStatus());
    }

    public ResponseEntity<Object> buildErrorResponse(Exception e, HttpStatus httpStatus) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("error", e);
        return new ResponseEntity(errorMap, httpStatus);
    }
}
