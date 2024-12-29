package org.parkinglot.app.service;

import org.parkinglot.app.map.SlotMapper;
import org.parkinglot.app.map.TicketMapper;
import org.parkinglot.app.map.VehicleMapper;
import org.parkinglot.app.repo.ParkingLotRepository;
import org.parkinglot.app.repo.SlotRepository;
import org.parkinglot.app.repo.TicketRepository;
import org.parkinglot.app.repo.VehicleRepository;
import org.parkinglot.app.repo.entity.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final SlotRepository slotRepository;
    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;
    
    public ParkingLotService(){
        this.parkingLotRepository = new ParkingLotRepository();
        this.slotRepository = new SlotRepository();
        this.vehicleRepository = new VehicleRepository();
        this.ticketRepository = new TicketRepository();
    }
    
    public void createParkingLot(Integer floors, Integer slotsPerFloor){
        HashMap<VehicleType, Integer> slotMap = generateSlotMap(slotsPerFloor);
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setFloors(floors);
        parkingLot.setSlotsPerFloor(slotsPerFloor);
        parkingLot.setSlotMap(slotMap);
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        generateSlotsForParkingLot(savedParkingLot);
        System.out.printf("Created parking lot %s with %d floors and %d slots per floor%n", savedParkingLot.getId(), floors, slotsPerFloor);
    }

    public void parkVehicle(VehicleType vehicleType, String registrationNumber, String color){
        Slot slot = slotRepository.findByVehicleTypeAndSlotStatus(vehicleType, SlotStatus.VACANT);

        if(slot == null){
            System.out.println("Parking lot is full");
            return;
        }

        Vehicle vehicle = VehicleMapper.createVehicle(vehicleType, registrationNumber, color);
        vehicleRepository.save(vehicle);
        slot.setSlotStatus(SlotStatus.OCCUPIED);
        Ticket ticket = TicketMapper.createTicket(slot, vehicle);
        String ticketId = ticketRepository.save(ticket);
        System.out.printf("Parked Vehicle. Ticket Id:%s%n", ticketId);

    }

    public void unparkVehicle(String ticketId){
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        if(ticket == null){
            System.out.println("Invalid Ticket");
            return;
        }
        // set exit time
        ticket.setExitTime(LocalDateTime.now());
        // Change ticket status
        ticket.setTicketStatus(TicketStatus.INACTIVE);
        // Open slot
        Slot slot = ticket.getSlot();
        slot.setSlotStatus(SlotStatus.VACANT);
        // Calculate amount
        calculateAndSetAmount(ticket);
        // Get vehicle details
        Vehicle vehicle = ticket.getVehicle();
        System.out.printf("Unparked Vehicle with Registration Number:%s, Color:%s and Amount:%.2f%n", vehicle.getRegistrationNumber(), vehicle.getColor(), ticket.getAmount());
    }

    public void displayFreeCount(VehicleType vehicleType){
        List<Slot> slots  = slotRepository.findAllByVehicleTypeAndSlotStatus(vehicleType, SlotStatus.VACANT);
        Map<Integer, Long> countByFloor = slots.stream()
                .collect(Collectors.groupingBy(Slot::getFloorNumber, Collectors.counting()));
        for(int floorNumber = 1; floorNumber <= parkingLotRepository.getParkingLot().getFloors(); floorNumber++){
            if(!countByFloor.containsKey(floorNumber)){
                countByFloor.put(floorNumber, 0L);
            }
        }
        countByFloor.forEach((floorNum, slotCount) -> System.out.printf("No. of free slots for %s on Floor %d: %d%n", vehicleType.name(), floorNum, slotCount));
    }

    public void displayFreeSlots(VehicleType vehicleType){
        List<Slot> slots = slotRepository.findAllByVehicleTypeAndSlotStatus(vehicleType, SlotStatus.VACANT);
        for(int floorNumber = 1; floorNumber <= parkingLotRepository.getParkingLot().getFloors(); floorNumber++){
            System.out.printf("No. of free slots for %s on Floor %d: ", vehicleType.name(), floorNumber);
            int finalFloorNumber = floorNumber;
            slots.forEach(slot -> {
                if(slot.getFloorNumber() == finalFloorNumber){
                    System.out.printf("%d ", slot.getSlotNumber());
                }
            });
            System.out.println();
        }
    }

    public void displayOccupiedSlots(VehicleType vehicleType){
        List<Slot> slots = slotRepository.findAllByVehicleTypeAndSlotStatus(vehicleType, SlotStatus.OCCUPIED);
        for(int floorNumber = 1; floorNumber <= parkingLotRepository.getParkingLot().getFloors(); floorNumber++){
            System.out.printf("Occupied slots for %s on Floor %d: ", vehicleType.name(), floorNumber);
            int finalFloorNumber = floorNumber;
            slots.forEach(slot -> {
                if(slot.getFloorNumber() == finalFloorNumber){
                    System.out.printf("%d ", slot.getSlotNumber());
                }
            });
            System.out.println();
        }
    }

    private static void calculateAndSetAmount(Ticket ticket){
        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = ticket.getExitTime();
        long hours = entryTime.until(exitTime, ChronoUnit.HOURS);
        if(hours == 0){
            hours = 1;
        }
        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        VehicleCharges rate = VehicleCharges.getFromName(vehicleType.name());
        ticket.setAmount(hours * rate.getChargesPerHour());
    }

    private HashMap<VehicleType, Integer> generateSlotMap(Integer slotsPerFloor){
        HashMap<VehicleType, Integer> slotMap = new HashMap<>();
        if (slotsPerFloor < 4){
            throw new IllegalArgumentException("Minimum 4 slots are required per floor");
        }
        slotMap.put(VehicleType.TRUCK, 1);
        slotsPerFloor -= 1;
        slotMap.put(VehicleType.BIKE, 2);
        slotsPerFloor -= 2;
        slotMap.put(VehicleType.CAR, slotsPerFloor);
        return slotMap;
    }

    private void generateSlotsForParkingLot(ParkingLot parkingLot){
        List<Slot> slotList = new ArrayList<>();
        for(int floorNumber = 1; floorNumber <= parkingLot.getFloors(); floorNumber++){
            int currentSlotNumber = 1;
            for(VehicleType vehicleType: parkingLot.getSlotMap().keySet()){
                Integer slots = parkingLot.getSlotMap().get(vehicleType);
                for(int slotNumber = 0; slotNumber < slots; slotNumber++){
                    Slot slot = SlotMapper.createSlot(floorNumber, currentSlotNumber++, vehicleType, parkingLot);
                    slotList.add(slot);
                }
            }
        }
        slotRepository.saveAll(slotList);
    }


}
