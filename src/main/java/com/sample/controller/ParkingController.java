package com.sample.controller;


import com.sample.model.CarParkRequest;
import com.sample.model.CarParking;
import com.sample.service.ParkingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking/api/v1")
public class ParkingController {

    private ParkingService parkingService;


    public ParkingController(final ParkingService parkingService){
        this.parkingService = parkingService;
    }

    @RequestMapping(value = "getParking",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public CarParking getParking(@RequestBody CarParkRequest carParkRequest){
        return parkingService.getParking(carParkRequest);
    }

    @RequestMapping(value = "allocate",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CarParking allocate(@RequestBody CarParkRequest carParkRequest){
        return parkingService.allocateParking(carParkRequest);

    }

    @RequestMapping(value = "release",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public CarParking release(@RequestBody CarParkRequest carParkRequest){
        return parkingService.releaseParking(carParkRequest);
    }

}