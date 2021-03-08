package com.example.parking.service;

import com.example.parking.constants.ParkingSpotType;
import com.example.parking.constants.Status;
import com.example.parking.constants.VehicleType;
import com.example.parking.dto.request.ParkReq;
import com.example.parking.dto.responses.ParkingResponse;
import com.example.parking.exceptions.ParkingException;
import com.example.parking.model.ParkingSpotParkingFloor;
import com.example.parking.model.Vehicle;
import com.example.parking.repositories.DataBaseRepository;
import com.example.parking.repositories.ParkingFloorRepository;
import com.example.parking.repositories.ParkingLotRepository;
import com.example.parking.repositories.ParkingRepository;
import com.example.parking.repositories.ParkingSpotRepository;
import com.example.parking.repositories.VehicleRepository;
import com.example.parking.services.impl.ParkingServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceImplTest {

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Mock
    private DataBaseRepository dataBaseRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private ParkingFloorRepository parkingFloorRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingRepository parkingRepository;

    @Test
    public void test() throws ParkingException, SQLException {
        Mockito.when(dataBaseRepository.getDBConnection()).thenReturn(Mockito.any());
        Mockito.when(parkingLotRepository.getParkingLotId(Mockito.any(), Mockito.any())).thenReturn("12");
        Mockito.when(parkingSpotRepository.getParkingSpotId(Mockito.any())).thenReturn(Mockito.any());
        ParkingSpotParkingFloor parkingSpotParkingFloor = new ParkingSpotParkingFloor();
        parkingSpotParkingFloor.setParkingSpotId("ps1");
        parkingSpotParkingFloor.setParkingFloorId("pf1");
        Mockito.when(parkingSpotRepository.getNormalParkingSpot(Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(parkingSpotParkingFloor);
        Mockito.when(parkingFloorRepository.updateAvailableParkingSlots(Mockito.anyString(), false, Mockito.any())).thenReturn(true);
        Mockito.when(vehicleRepository.insertVehilce(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.any())).thenReturn(1);
        Mockito.when(parkingRepository.insertParking(Mockito.anyInt(), Status.PARK, Mockito.any())).thenReturn(1);
        ParkReq parkReq = new ParkReq();
        parkReq.setParkingLotName("pk1");
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(VehicleType.BIKE);
        parkReq.setVehicle(vehicle);
        ParkingResponse parkingResponse = parkingService.park(parkReq);
        Assert.assertEquals(1, parkingResponse.getSpotId());
    }


}
