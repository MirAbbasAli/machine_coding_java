package org.hotel.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TransformUtils {
    public static LocalDate parseToLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String generateBookingId(String userName, Integer roomNumber, Integer count){
        return String.format("%s-%d-%d", userName, roomNumber, count);
    }

    public static Double calculateAmount(Double pricePerDay, LocalDate checkIn, LocalDate checkOut){
        return pricePerDay * ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public static Boolean intersectedInterval(LocalDate startDateOne, LocalDate endDateOne, LocalDate startDateTwo, LocalDate endDateTwo){
        return !startDateTwo.isAfter(endDateOne) && !startDateOne.isAfter(endDateTwo);
    }
}
