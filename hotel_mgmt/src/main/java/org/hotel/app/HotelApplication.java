package org.hotel.app;

import org.hotel.app.entity.Booking;
import org.hotel.app.entity.Room;
import org.hotel.app.entity.service.BookingService;
import org.hotel.app.entity.service.RoomService;
import org.hotel.app.entity.service.UserService;
import org.hotel.app.util.TransformUtils;

import java.util.List;
import java.util.Scanner;

public class HotelApplication {
    private static final Scanner in = new Scanner(System.in);
    private static void addUsers(UserService userService){
        userService.addUser("user1");
        userService.addUser("user2");
    }
    public static void main(String[] args){
        boolean running = true;
        UserService userService = new UserService();
        RoomService roomService = new RoomService();
        addUsers(userService);
        BookingService bookingService = new BookingService(userService, roomService);
        String invalidInput = "Invalid input!!";
        while(running){
            try{
                String command = in.nextLine();
                String[] parts = command.split(" ");
                List<Booking> bookings;
                List<Room> rooms;
                Booking booking;
                switch(parts[0]){
                    case "exit":
                        running = false;
                        break;
                    case "add-rooms":
                        String[] roomInfo = parts[1].split(",");
                        List<Integer> roomNumbers = roomService.addRooms(roomInfo[0], Double.parseDouble(roomInfo[1]), Integer.parseInt(roomInfo[2]));
                        System.out.printf("Created %s Rooms: ",roomInfo[0]);
                        roomNumbers.forEach(room->System.out.print(room+" "));
                        System.out.println();
                        break;
                    case "view-bookings":
                        bookings = bookingService.viewRoomAllBookings(Integer.parseInt(parts[1]));
                        if(bookings.isEmpty()) {
                            System.out.println("No Bookings");
                        } else {
                            bookings.forEach(System.out::println);
                        }
                        break;
                    case "search-room":
                        rooms = bookingService.searchRooms(parts[1], TransformUtils.parseToLocalDate(parts[2]), TransformUtils.parseToLocalDate(parts[3]));
                        if(rooms.isEmpty()){
                            System.out.println("No Rooms Available");
                        } else {
                            System.out.print("Available Rooms: ");
                            rooms.forEach(room -> {
                                System.out.print(room.getNumber()+" ");
                            });
                            System.out.println();
                        }
                        break;
                    case "book-room":
                        booking = bookingService.bookRoom(
                                Integer.parseInt(parts[1]),
                                parts[2],
                                TransformUtils.parseToLocalDate(parts[3]),
                                TransformUtils.parseToLocalDate(parts[4])
                        );
                        if(booking == null){
                            System.out.println("No Bookings made");
                        } else {
                            System.out.println(booking);
                        }
                        break;
                    case "cancel-booking":
                        bookingService.cancelBooking(parts[1]);
                        break;
                    case "view-my-bookings":
                        bookings = bookingService.viewUserBookings(parts[1]);
                        if(bookings.isEmpty()){
                            System.out.println("No Bookings made");
                        } else {
                            bookings.forEach(System.out::println);
                        }
                        break;
                    case "check-in":
                        bookingService.checkIn(parts[1]);
                        break;
                    case "check-out":
                        bookingService.checkOut(parts[1]);
                        break;
                    default:
                        System.out.println(invalidInput);
                        break;
                }
            } catch (Exception e) {
                System.out.println(invalidInput);
            }
        }
    }
}
