// SlotManager - Single Responsibility: Manages slot allocation
import java.util.*;

public class SlotManager {
    private final Map<String, ParkingSlot> slots;
    private final SlotAllocationStrategy allocationStrategy;
    
    public SlotManager(Map<SlotType, Integer> slotCapacity) {
        this.slots = new HashMap<>();
        this.allocationStrategy = new NearestSlotAllocationStrategy();
        initializeSlots(slotCapacity);
    }
    
    private void initializeSlots(Map<SlotType, Integer> slotCapacity) {
        int counter = 1;
        for (Map.Entry<SlotType, Integer> entry : slotCapacity.entrySet()) {
            SlotType type = entry.getKey();
            int capacity = entry.getValue();
            
            for (int i = 0; i < capacity; i++) {
                String slotId = type + "-" + counter++;
                String gateId = "GATE-" + (i % 3 + 1); // Distribute across 3 gates
                ParkingSlot slot = new ParkingSlot(slotId, type, gateId);
                slots.put(slotId, slot);
            }
        }
    }
    
    public ParkingSlot findNearestSlot(VehicleType vehicleType, SlotType requestedSlotType, String entryGateId) {
        List<SlotType> compatibleSlots = getCompatibleSlotTypes(vehicleType, requestedSlotType);
        return allocationStrategy.findSlot(slots.values(), compatibleSlots, entryGateId);
    }
    
    private List<SlotType> getCompatibleSlotTypes(VehicleType vehicleType, SlotType requestedSlotType) {
        List<SlotType> compatible = new ArrayList<>();
        
        switch (vehicleType) {
            case TWO_WHEELER:
                if (requestedSlotType != null) {
                    compatible.add(requestedSlotType);
                } else {
                    compatible.addAll(Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE));
                }
                break;
            case CAR:
                if (requestedSlotType == SlotType.MEDIUM || requestedSlotType == SlotType.LARGE) {
                    compatible.add(requestedSlotType);
                } else {
                    compatible.addAll(Arrays.asList(SlotType.MEDIUM, SlotType.LARGE));
                }
                break;
            case BUS:
                compatible.add(SlotType.LARGE);
                break;
        }
        
        return compatible;
    }
    
    public Map<SlotType, Integer> getAvailability() {
        Map<SlotType, Integer> availability = new HashMap<>();
        availability.put(SlotType.SMALL, 0);
        availability.put(SlotType.MEDIUM, 0);
        availability.put(SlotType.LARGE, 0);
        
        for (ParkingSlot slot : slots.values()) {
            if (slot.isAvailable()) {
                availability.put(slot.getType(), availability.get(slot.getType()) + 1);
            }
        }
        
        return availability;
    }
    
    public ParkingSlot getSlotById(String slotId) {
        return slots.get(slotId);
    }
}
