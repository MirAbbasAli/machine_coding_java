package org.hotel.app.entity.service;

import org.hotel.app.entity.*;
import org.hotel.app.exception.BookingNotFoundException;
import org.hotel.app.util.TransformUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingService {
    private final List<Booking> bookings;
    private final RoomService roomService;
    private final UserService userService;

    public BookingService(UserService userService, RoomService roomService){
        this.roomService = roomService;
        this.userService = userService;
        bookings = new ArrayList<>();
    }
    public List<Booking> viewRoomAllBookings(Integer roomNumber){
        return bookings.stream()
                .filter(booking -> Objects.equals(booking.getRoom().getNumber(), roomNumber))
                .toList();
    }

    public List<Room> searchRooms(String roomType, LocalDate checkInDate, LocalDate checkOutDate){
        RoomType type = RoomType.getRoomType(roomType);
        List<Room> totalRooms = new ArrayList<>(roomService.findByRoomType(type));
        List<Room> bookedRooms = bookings.stream()
                .filter(booking -> booking.getRoomStatus().equals(RoomStatus.BOOKED) && booking.getRoom().getType().equals(type) && TransformUtils.intersectedInterval(booking.getCheckIn(), booking.getCheckOut(), checkInDate, checkOutDate))
                .map(Booking::getRoom)
                .toList();
        totalRooms.removeAll(bookedRooms);
        return totalRooms;
    }

    public Booking bookRoom(Integer roomNumber, String userName, LocalDate checkInDate, LocalDate checkOutDate){
        Room room = roomService.findByNumber(roomNumber);
        if(checkRoomAvailability(room, checkInDate, checkOutDate)){
            return null;
        }
        User user = userService.findByName(userName);
        String bookingId = TransformUtils.generateBookingId(userName, roomNumber, bookings.size() + 1);
        Booking booking = new Booking(bookingId, checkInDate, checkOutDate, room, user);
        bookings.add(booking);
        return booking;
    }

    public void cancelBooking(String bookingId){
        Booking booking = findById(bookingId);
        if(booking.getBookingStatus() == BookingStatus.BOOKED) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            booking.setRoomStatus(RoomStatus.AVAILABLE);
            System.out.printf("Booking %s cancelled%n", bookingId);
        } else {
            System.out.printf("Booking cannot be %s, because user has already %s%n",BookingStatus.CANCELLED, booking.getBookingStatus());
        }
    }

    public void checkIn(String bookingId){
        Booking booking = findById(bookingId);
        if(booking.getBookingStatus() == BookingStatus.BOOKED) {
            booking.setBookingStatus(BookingStatus.CHECKED_IN);
            System.out.printf("Checked in: %s%n", booking);
        } else {
            System.out.printf("Booking cannot be %s, because user has already %s%n",BookingStatus.CHECKED_IN, booking.getBookingStatus());
        }
    }

    public void checkOut(String bookingId){
        Booking booking = findById(bookingId);
        if(booking.getBookingStatus() == BookingStatus.CHECKED_IN) {
            booking.setBookingStatus(BookingStatus.CHECKED_OUT);
            booking.setRoomStatus(RoomStatus.AVAILABLE);
            System.out.printf("Checked Out: %s and the price: %.2f%n", booking, TransformUtils.calculateAmount(booking.getRoom().getPricePerDay(), booking.getCheckIn(), booking.getCheckOut()));
        } else {
            System.out.printf("Booking cannot be %s, because user has not %s%n", BookingStatus.CHECKED_OUT, BookingStatus.CHECKED_IN);
        }
    }

    public List<Booking> viewUserBookings(String userName){
        User user = userService.findByName(userName);
        return bookings.stream()
                .filter(booking -> booking.getUser().equals(user))
                .toList();
    }

    private Boolean checkRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate){
        return !bookings.isEmpty() && bookings.stream()
                .anyMatch(booking -> booking.getRoom().equals(room)
                        && booking.getRoomStatus() == RoomStatus.AVAILABLE
                        && TransformUtils.intersectedInterval(booking.getCheckIn(), booking.getCheckOut(), checkInDate, checkOutDate)
                );
    }

    private Booking findById(String bookingId){
        return bookings.stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(bookingId + " does not exist!!"));
    }
}
