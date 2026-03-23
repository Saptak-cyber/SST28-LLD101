// ParkingSlot and SlotType
public class ParkingSlot {
    private final String slotId;
    private final SlotType type;
    private final String gateId;
    private boolean isOccupied;
    private Vehicle parkedVehicle;
    
    public ParkingSlot(String slotId, SlotType type, String gateId) {
        this.slotId = slotId;
        this.type = type;
        this.gateId = gateId;
        this.isOccupied = false;
    }
    
    public void occupy(Vehicle vehicle) {
        if (isOccupied) {
            throw new RuntimeException("Slot already occupied");
        }
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
    }
    
    public void vacate() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }
    
    public boolean isAvailable() {
        return !isOccupied;
    }
    
    public String getSlotId() {
        return slotId;
    }
    
    public SlotType getType() {
        return type;
    }
    
    public String getGateId() {
        return gateId;
    }
}

enum SlotType {
    SMALL,
    MEDIUM,
    LARGE
}
