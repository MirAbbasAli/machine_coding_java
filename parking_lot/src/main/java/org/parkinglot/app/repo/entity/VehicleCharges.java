package org.parkinglot.app.repo.entity;

import java.util.Arrays;

public enum VehicleCharges {
    TRUCK(50.0),
    CAR(30.0),
    BIKE(20.0);

    private final Double chargesPerHour;

    VehicleCharges(Double chargesPerHour) {
        this.chargesPerHour = chargesPerHour;
    }

    public Double getChargesPerHour() {
        return chargesPerHour;
    }

    public static VehicleCharges getFromName(String name){
        return Arrays.stream(VehicleCharges.values())
                .filter(vehicleCharges -> vehicleCharges.name().equals(name))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Invalid Vehicle Type"));
    }
}
