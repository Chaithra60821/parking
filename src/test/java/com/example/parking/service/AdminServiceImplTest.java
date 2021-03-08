package com.example.parking.service;

import com.example.parking.constants.ParkingSpotType;
import com.example.parking.dto.request.CreateParkingLot;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.repositories.DataBaseRepository;
import com.example.parking.repositories.ParkingFloorRepository;
import com.example.parking.repositories.ParkingLotRepository;
import com.example.parking.repositories.ParkingSpotRepository;
import com.example.parking.services.impl.AdminServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private DataBaseRepository dataBaseRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private ParkingFloorRepository parkingFloorRepository;


    @Test
    public void adminServiceTest() throws SQLException, ParkingException {
        CreateParkingLot createParkingLot = new CreateParkingLot();
        createParkingLot.setParkingLotName("parkinLot1");
        createParkingLot.setNoParkingFloors(10);
        Mockito.when(dataBaseRepository.getDBConnection()).thenReturn(Mockito.any());
        Mockito.when(parkingLotRepository.createParkingLot(createParkingLot, Mockito.any())).thenReturn("PakingLotId");
        Mockito.when(parkingSpotRepository.getParkingSpotId(ParkingSpotType.BIKE)).thenReturn(1);
        Mockito.when(parkingSpotRepository.getParkingSpotId(ParkingSpotType.CAR)).thenReturn(2);
        Mockito.when(parkingFloorRepository.createParkingFloor(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn("parkingfloor");
        Mockito.when(parkingSpotRepository.createParkingSpot(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn("parkingSpotId");
        String parkinglot = adminService.createParkingLot(createParkingLot);
        Assert.assertEquals("parkinLot1", parkinglot);
    }
}
