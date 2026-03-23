// Main ParkingLot class - Facade for the system
import java.util.*;

public class ParkingLot {
    private final SlotManager slotManager;
    private final BillingService billingService;
    private final TicketService ticketService;
    
    public ParkingLot(Map<SlotType, Integer> slotCapacity, Map<SlotType, Double> hourlyRates) {
        this.slotManager = new SlotManager(slotCapacity);
        this.billingService = new BillingService(hourlyRates);
        this.ticketService = new TicketService();
    }
    
    public ParkingTicket park(Vehicle vehicle, Date entryTime, SlotType requestedSlotType, String entryGateID) {
        ParkingSlot slot = slotManager.findNearestSlot(vehicle.getType(), requestedSlotType, entryGateID);
        if (slot == null) {
            throw new RuntimeException("No available slot for vehicle type: " + vehicle.getType());
        }
        
        slot.occupy(vehicle);
        return ticketService.generateTicket(vehicle, slot, entryTime);
    }
    
    public Map<SlotType, Integer> status() {
        return slotManager.getAvailability();
    }
    
    public double exit(ParkingTicket ticket, Date exitTime) {
        ParkingSlot slot = slotManager.getSlotById(ticket.getSlotNumber());
        slot.vacate();
        
        double amount = billingService.calculateBill(ticket, exitTime);
        ticketService.closeTicket(ticket);
        return amount;
    }
}
