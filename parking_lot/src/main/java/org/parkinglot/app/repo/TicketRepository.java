package org.parkinglot.app.repo;

import org.parkinglot.app.repo.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private final List<Ticket> tickets;

    public TicketRepository() {
        this.tickets = new ArrayList<>();
    }

    public String save(Ticket ticket) {
        String id = String.format("%s_%s_%s", ticket.getSlot().getParkingLot().getId(), ticket.getSlot().getFloorNumber(), ticket.getSlot().getSlotNumber());
        ticket.setId(id);
        tickets.add(ticket);
        return ticket.getId();
    }

    public Ticket findTicketById(String id){
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(String.format("Ticket %s not found", id)));
    }

}
