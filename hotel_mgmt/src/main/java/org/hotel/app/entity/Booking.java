package org.hotel.app.entity;

import java.time.LocalDate;

public class Booking {
    private final String id;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final Room room;
    private final User user;
    private BookingStatus bookingStatus;
    private RoomStatus roomStatus;

    public Booking(String id, LocalDate checkIn, LocalDate checkOut, Room room, User user) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.user = user;
        this.bookingStatus = BookingStatus.BOOKED;
        this.roomStatus = RoomStatus.BOOKED;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getId() {
        return id;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + getId() + '\'' +
                ", checkIn=" + getCheckIn() +
                ", checkOut=" + getCheckOut() +
                ", room=" + room.getNumber() +
                ", user=" + user.getName() +
                ", bookingStatus=" + getBookingStatus() +
                ", roomStatus=" + getRoomStatus() +
                '}';
    }
}
