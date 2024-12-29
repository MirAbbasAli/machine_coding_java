package org.parkinglot.app.repo;

import org.parkinglot.app.repo.entity.ParkingLot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotRepository {
    private final List<ParkingLot> parkingLots;
    private static Integer count = 1;
    private static final String PARKING_LOT_PREFIX = "PR";

    public ParkingLotRepository() {
        this.parkingLots = new ArrayList<>();
    }

    public ParkingLot save(ParkingLot parkingLot) {
        String id = PARKING_LOT_PREFIX + count++;
        parkingLot.setId(id);
        parkingLots.add(parkingLot);
        return parkingLots.get(0);
    }

    public ParkingLot getParkingLot(){
        return parkingLots.get(0);
    }
}
