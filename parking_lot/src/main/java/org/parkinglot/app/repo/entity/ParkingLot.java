package org.parkinglot.app.repo.entity;

import java.util.HashMap;

public class ParkingLot {
    private String id;
    private Integer floors;
    private Integer slotsPerFloor;
    private HashMap<VehicleType, Integer> slotMap;

    public ParkingLot(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getSlotsPerFloor() {
        return slotsPerFloor;
    }

    public void setSlotsPerFloor(Integer slotsPerFloor) {
        this.slotsPerFloor = slotsPerFloor;
    }

    public HashMap<VehicleType, Integer> getSlotMap() {
        return slotMap;
    }

    public void setSlotMap(HashMap<VehicleType, Integer> slotMap) {
        this.slotMap = slotMap;
    }
}
