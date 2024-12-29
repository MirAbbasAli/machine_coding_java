package org.parkinglot.app.repo;

import org.parkinglot.app.repo.entity.Slot;
import org.parkinglot.app.repo.entity.SlotStatus;
import org.parkinglot.app.repo.entity.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class SlotRepository {
    private final List<Slot> slots;
    private static Integer count = 1;

    public SlotRepository() {
        slots = new ArrayList<>();
    }

    public void saveAll(List<Slot> slotList){
        for(Slot slot: slotList){
            slot.setId(count++);
            slots.add(slot);
        }
    }

    public Slot findByVehicleTypeAndSlotStatus(VehicleType vehicleType, SlotStatus slotStatus){
        return slots.stream()
                .filter(slot -> slot.getVehicleType().equals(vehicleType) && slot.getSlotStatus().equals(slotStatus))
                .findFirst()
                .orElse(null);
    }

    public List<Slot> findAllByVehicleTypeAndSlotStatus(VehicleType vehicleType, SlotStatus slotStatus){
        return slots.stream()
                .filter(slot -> slot.getVehicleType().equals(vehicleType) && slot.getSlotStatus().equals(slotStatus))
                .toList();
    }
}
