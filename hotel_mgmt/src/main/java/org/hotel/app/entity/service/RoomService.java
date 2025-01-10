package org.hotel.app.entity.service;

import org.hotel.app.entity.Room;
import org.hotel.app.entity.RoomType;
import org.hotel.app.exception.RoomNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoomService {
    private final List<Room> rooms;

    public RoomService(){
        rooms = new ArrayList<>();
    }

    public List<Integer> addRooms(String roomTypeName, Double price, Integer numOfRooms){
        return IntStream.rangeClosed(1, numOfRooms)
                .mapToObj(roomCount -> {
                    int roomNumber = 100 + rooms.size() + 1;
                    RoomType roomType = RoomType.getRoomType(roomTypeName);
                    Room room = new Room(roomNumber, roomType, price);
                    rooms.add(room);
                    return roomNumber;
                })
                .collect(Collectors.toList());

    }

    public Room findByNumber(Integer roomNumber){
        return rooms.stream()
                .filter(room -> Objects.equals(room.getNumber(), roomNumber))
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException(roomNumber + " does not exists in the Hotel"));
    }

    public List<Room> findByRoomType(RoomType roomType){
        return rooms.stream()
                .filter(room -> room.getType().equals(roomType))
                .toList();
    }
}
