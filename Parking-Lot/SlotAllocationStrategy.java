// Strategy Pattern - Open/Closed Principle
import java.util.*;

interface SlotAllocationStrategy {
    ParkingSlot findSlot(Collection<ParkingSlot> slots, List<SlotType> compatibleTypes, String entryGateId);
}

class NearestSlotAllocationStrategy implements SlotAllocationStrategy {
    @Override
    public ParkingSlot findSlot(Collection<ParkingSlot> slots, List<SlotType> compatibleTypes, String entryGateId) {
        // First try to find slot at the same gate
        for (SlotType type : compatibleTypes) {
            for (ParkingSlot slot : slots) {
                if (slot.isAvailable() && slot.getType() == type && slot.getGateId().equals(entryGateId)) {
                    return slot;
                }
            }
        }
        
        // If not found, find any available compatible slot
        for (SlotType type : compatibleTypes) {
            for (ParkingSlot slot : slots) {
                if (slot.isAvailable() && slot.getType() == type) {
                    return slot;
                }
            }
        }
        
        return null;
    }
}
