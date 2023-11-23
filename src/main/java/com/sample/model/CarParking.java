package com.sample.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParking {


    private String reg;

    private String parkingSlot;

    private String startTime;

    private String endTime;

    private String message;

    private String cost;

}