package org.parkinglot.app.map;

import org.parkinglot.app.repo.entity.ParkingLot;
import org.parkinglot.app.repo.entity.Slot;
import org.parkinglot.app.repo.entity.SlotStatus;
import org.parkinglot.app.repo.entity.VehicleType;

public class SlotMapper {
    public static Slot createSlot(int floorNumber, int slotNumber, VehicleType vehicleType, ParkingLot parkingLot){
        Slot slot = new Slot();
        slot.setFloorNumber(floorNumber);
        slot.setSlotNumber(slotNumber);
        slot.setVehicleType(vehicleType);
        slot.setParkingLot(parkingLot);
        slot.setSlotStatus(SlotStatus.VACANT);

        return slot;
    }
}
