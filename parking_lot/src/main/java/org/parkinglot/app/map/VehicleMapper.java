package org.parkinglot.app.map;

import org.parkinglot.app.repo.entity.Vehicle;
import org.parkinglot.app.repo.entity.VehicleType;

public class VehicleMapper {
    public static Vehicle createVehicle(VehicleType vehicleType, String registrationNumber, String color) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleType);
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setColor(color);
        return vehicle;
    }
}
