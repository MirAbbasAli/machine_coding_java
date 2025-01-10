package org.hotel.app.entity;

import org.hotel.app.exception.RoomTypeNotFoundException;

import java.util.Arrays;

public enum RoomType {
    SINGLE, DOUBLE, SUITE;

    public static RoomType getRoomType(String name){
        return Arrays.stream(RoomType.values())
                .filter(roomType -> roomType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RoomTypeNotFoundException(name + "not found"));
    }
}
