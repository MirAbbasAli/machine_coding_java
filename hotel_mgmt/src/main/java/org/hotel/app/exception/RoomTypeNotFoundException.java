package org.hotel.app.exception;

public class RoomTypeNotFoundException  extends IllegalArgumentException{
    public RoomTypeNotFoundException(String message) {
        super(message);
    }
}