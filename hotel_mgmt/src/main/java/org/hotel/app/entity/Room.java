package org.hotel.app.entity;

import java.util.Objects;

public class Room{
    private final Integer number;
    private final RoomType type;
    private final Double pricePerDay;

    public Room(Integer number, RoomType type, Double pricePerDay) {
        this.number = number;
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    public Integer getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    @Override
    public String toString() {
        return getNumber()+" ";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Room room)) return false;
        return getNumber()!=null && Objects.equals(getNumber(), room.getNumber());
    }
}
