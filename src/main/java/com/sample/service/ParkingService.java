package com.sample.service;

import com.sample.model.CarParkRequest;
import com.sample.model.CarParking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ParkingService {


    @Value("${parking.slots}")
    public List<String> parkingSlots;


    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));;


    private ConcurrentHashMap<String,CarParking> allocatedParking = new ConcurrentHashMap<>();


    public CarParking allocateParking(final CarParkRequest carParkRequest){
        if(allocatedParking.keySet().size() == parkingSlots.size()){
            return new CarParking(carParkRequest.getReg(),null, null,null,"Parking is full",null);
        }
        String parkingSlot = parkingSlots.stream().filter(x-> !allocatedParking.containsKey(x)).findFirst().get();
        CarParking carParking = new CarParking(carParkRequest.getReg(),parkingSlot,
                ZonedDateTime.now().format(dateTimeFormatter),null,"Parking Allocated",null);
        allocatedParking.put(parkingSlot,carParking);
        return carParking;
    }


    public CarParking getParking(CarParkRequest carParkRequest){

        if(allocatedParking.containsKey(carParkRequest.getParkingSlot()) &&
                allocatedParking.get(carParkRequest.getParkingSlot()).getReg().equalsIgnoreCase(carParkRequest.getReg())){
            return allocatedParking.get(carParkRequest.getParkingSlot());
        }
        return new CarParking(carParkRequest.getReg(),carParkRequest.getParkingSlot()
                , null,null,"Invalid Reg/Parking Slot combination",null);

    }



    public CarParking releaseParking(CarParkRequest carParkRequest){
        String parkingSlot = carParkRequest.getParkingSlot();
        String reg = carParkRequest.getReg();
        if(allocatedParking.containsKey(parkingSlot) && allocatedParking.get(parkingSlot).getReg().equalsIgnoreCase(reg)){
            CarParking carParking = allocatedParking.get(parkingSlot);
            allocatedParking.remove(parkingSlot);

            ZonedDateTime startTime = ZonedDateTime.parse(carParking.getStartTime(), dateTimeFormatter);           ;
            ZonedDateTime endTime = ZonedDateTime.parse("2023-11-22 19:50:28", dateTimeFormatter);
            double hours = Double.valueOf(ChronoUnit.SECONDS.between(startTime,endTime))/Double.valueOf("3600");
            long cost = (int) Math.ceil(hours)*2;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.UK);
            String costStr = nf.format(cost);
            carParking.setEndTime(endTime.format(dateTimeFormatter));
            carParking.setCost(costStr);
            return carParking;
        }
        return new CarParking(reg,parkingSlot, null,null,"Invalid Reg/Parking Slot combination",null);
    }


}