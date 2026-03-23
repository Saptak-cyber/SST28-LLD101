// BillingService - Single Responsibility: Handles billing calculations
import java.util.*;

public class BillingService {
    private final Map<SlotType, Double> hourlyRates;
    
    public BillingService(Map<SlotType, Double> hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
    
    public double calculateBill(ParkingTicket ticket, Date exitTime) {
        long durationMillis = exitTime.getTime() - ticket.getEntryTime().getTime();
        double hours = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        
        double hourlyRate = hourlyRates.get(ticket.getSlotType());
        return hours * hourlyRate;
    }
}
