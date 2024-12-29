package org.parkinglot.app.map;

import org.parkinglot.app.repo.entity.Slot;
import org.parkinglot.app.repo.entity.Ticket;
import org.parkinglot.app.repo.entity.TicketStatus;
import org.parkinglot.app.repo.entity.Vehicle;

import java.time.LocalDateTime;

public class TicketMapper {
    public static Ticket createTicket(Slot slot, Vehicle vehicle){
        Ticket ticket = new Ticket();
        ticket.setSlot(slot);
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setTicketStatus(TicketStatus.ACTIVE);
        return ticket;
    }
}
