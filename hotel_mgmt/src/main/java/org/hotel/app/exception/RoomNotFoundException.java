package org.hotel.app.exception;

public class RoomNotFoundException extends IllegalArgumentException{
    public RoomNotFoundException(String message) {
        super(message);
    }
}
