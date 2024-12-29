package org.parkinglot.app;

import org.parkinglot.app.repo.entity.VehicleType;
import org.parkinglot.app.service.ParkingLotService;

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class ParkingLotApplication {
    public static void main(String[] args){
        ParkingLotService parkingLotService = new ParkingLotService();

        boolean isRunning = true;
        Scanner in = new Scanner(System.in);
        while(isRunning){
            try {
                String userInput = in.nextLine();

                List<String> command = Arrays.asList(userInput.split(" "));
                VehicleType vehicleType;
                switch (command.get(0)) {
                    case "create_parking_lot":
                        int floors = Integer.parseInt(command.get(1));
                        int slotsPerFloor = Integer.parseInt(command.get(2));
                        parkingLotService.createParkingLot(floors, slotsPerFloor);
                        break;
                    case "park_vehicle":
                        vehicleType = VehicleType.getFromValue(command.get(1));
                        String registrationNumber = command.get(2);
                        String color = command.get(3);
                        parkingLotService.parkVehicle(vehicleType, registrationNumber, color);
                        break;
                    case "unpark_vehicle":
                        String ticketId = command.get(1);
                        parkingLotService.unparkVehicle(ticketId);
                        break;
                    case "display":
                        vehicleType = VehicleType.getFromValue(command.get(2));
                        switch(command.get(1)){
                            case "free_count":
                                parkingLotService.displayFreeCount(vehicleType);
                                break;
                            case "free_slots":
                                parkingLotService.displayFreeSlots(vehicleType);
                                break;
                            case "occupied_slots":
                                parkingLotService.displayOccupiedSlots(vehicleType);
                                break;
                        }
                        break;
                    case "exit":
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid command");
                }
            } catch (Exception e) {
                System.out.println("Invalid command");
                System.out.println(e.getMessage());
            }
        }
    }
}
