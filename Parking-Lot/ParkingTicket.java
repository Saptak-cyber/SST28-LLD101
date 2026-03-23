// ParkingTicket class
import java.util.Date;
import java.util.UUID;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final String slotNumber;
    private final SlotType slotType;
    private final Date entryTime;
    
    public ParkingTicket(Vehicle vehicle, String slotNumber, SlotType slotType, Date entryTime) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.entryTime = entryTime;
    }
    
    public String getTicketId() {
        return ticketId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public String getSlotNumber() {
        return slotNumber;
    }
    
    public SlotType getSlotType() {
        return slotType;
    }
    
    public Date getEntryTime() {
        return entryTime;
    }
}
