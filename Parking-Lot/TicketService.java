// TicketService - Single Responsibility: Manages tickets
import java.util.*;

public class TicketService {
    private final Map<String, ParkingTicket> activeTickets;
    
    public TicketService() {
        this.activeTickets = new HashMap<>();
    }
    
    public ParkingTicket generateTicket(Vehicle vehicle, ParkingSlot slot, Date entryTime) {
        ParkingTicket ticket = new ParkingTicket(vehicle, slot.getSlotId(), slot.getType(), entryTime);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }
    
    public void closeTicket(ParkingTicket ticket) {
        activeTickets.remove(ticket.getTicketId());
    }
    
    public ParkingTicket getTicket(String ticketId) {
        return activeTickets.get(ticketId);
    }
}
