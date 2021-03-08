package com.example.parking.controller;

import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.services.impl.AdminServiceImpl;
import com.example.parking.utils.ResponseEntityUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @InjectMocks
    private  AdminController adminController;

    @Mock
    private AdminServiceImpl adminService;

    @Mock
    private ResponseEntityUtil responseEntityUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void adminControllerTest() throws SQLException, ParkingException {
        CreateParkingLot createParkingLot = new CreateParkingLot();
        createParkingLot.setParkingLotName("pk1");
        createParkingLot.setNoParkingFloors(10);
        Mockito.when(adminService.createParkingLot(createParkingLot)).thenReturn("pl1");
        ResponseEntity responseEntity =
                responseEntityUtil.buildCreateParkingLotResponse("pk1");
        Mockito.when(responseEntityUtil.buildCreateParkingLotResponse("pk1")).thenReturn(responseEntity);
        ResponseEntity res = adminController.createParkingLot(createParkingLot);
        Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
    }
}
