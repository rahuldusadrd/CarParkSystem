package com.sample.service;

import com.sample.model.CarParkRequest;
import com.sample.model.CarParking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingServiceTest {


    ParkingService classUnderTest;

    private String slots = IntStream.range(1, 201).mapToObj(x->String.valueOf(x)).collect(Collectors.joining(","));

    @Test
    public void testAllocate(){
        classUnderTest = new ParkingService();
        ReflectionTestUtils.setField(classUnderTest,"parkingSlots",Arrays.asList(slots.split(",")));
        CarParkRequest carParkRequest = new CarParkRequest();
        carParkRequest.setReg("AAA");
        CarParking carParking = classUnderTest.allocateParking(carParkRequest);
        Assertions.assertEquals(carParking.getParkingSlot(),"1");
    }

    @Test
    public void testAllocateAndRelease(){
        classUnderTest = new ParkingService();
        ReflectionTestUtils.setField(classUnderTest,"parkingSlots",Arrays.asList(slots.split(",")));
        CarParkRequest carParkRequest = new CarParkRequest();
        carParkRequest.setReg("AAA");
        CarParking carParking = classUnderTest.allocateParking(carParkRequest);
        carParkRequest.setParkingSlot("1");
        CarParking releaseParking = classUnderTest.releaseParking(carParkRequest);
        Assertions.assertEquals(carParking.getParkingSlot(),"1");
        Assertions.assertEquals(releaseParking.getCost(),"£2.00");
    }

    @Test
    public void testAllocateWithFullCarPark(){

        classUnderTest = new ParkingService();
        ReflectionTestUtils.setField(classUnderTest,"parkingSlots", List.of("1"));
        CarParkRequest carParkRequest = new CarParkRequest();
        carParkRequest.setReg("AAA");
        CarParking carParking = classUnderTest.allocateParking(carParkRequest);
        carParkRequest.setParkingSlot("1");

        CarParkRequest carParkRequest1 = new CarParkRequest();
        carParkRequest.setReg("BBB");
        CarParking carParking1 = classUnderTest.allocateParking(carParkRequest1);
        Assertions.assertEquals(carParking.getParkingSlot(),"1");

        Assertions.assertEquals(carParking1.getMessage(),"Parking is full");
    }

}