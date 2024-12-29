package org.parkinglot.app.repo;

import org.parkinglot.app.repo.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    private final List<Vehicle> vehicles;
    private static Integer count = 1;

    public VehicleRepository() {
        this.vehicles = new ArrayList<>();
    }

    public void save(Vehicle vehicle) {
        vehicle.setId(count++);
        vehicles.add(vehicle);
    }
}
