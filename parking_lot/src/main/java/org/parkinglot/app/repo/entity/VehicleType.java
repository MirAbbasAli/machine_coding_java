package org.parkinglot.app.repo.entity;

import java.util.Arrays;

public enum VehicleType {
    TRUCK("TRUCK"),
    CAR("CAR"),
    BIKE("BIKE");

    private final String value;

    VehicleType(String value){
        this.value = value;
    }

    public static VehicleType getFromValue(String name){
        return Arrays.stream(VehicleType.values())
                .filter(vehicleType -> vehicleType.value.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Vehicle Type"));
    }
}
