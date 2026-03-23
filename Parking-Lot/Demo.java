// Demo class to test the system
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // Initialize parking lot with capacity
        Map<SlotType, Integer> capacity = new HashMap<>();
        capacity.put(SlotType.SMALL, 10);
        capacity.put(SlotType.MEDIUM, 20);
        capacity.put(SlotType.LARGE, 5);
        
        // Set hourly rates
        Map<SlotType, Double> rates = new HashMap<>();
        rates.put(SlotType.SMALL, 10.0);
        rates.put(SlotType.MEDIUM, 20.0);
        rates.put(SlotType.LARGE, 50.0);
        
        ParkingLot parkingLot = new ParkingLot(capacity, rates);
        
        // Test 1: Park a bike
        Vehicle bike = new Vehicle("KA-01-1234", VehicleType.TWO_WHEELER);
        Date entryTime1 = new Date();
        ParkingTicket ticket1 = parkingLot.park(bike, entryTime1, SlotType.SMALL, "GATE-1");
        System.out.println("Bike parked - Ticket: " + ticket1.getTicketId() + 
                          ", Slot: " + ticket1.getSlotNumber());
        
        // Test 2: Park a car
        Vehicle car = new Vehicle("KA-02-5678", VehicleType.CAR);
        Date entryTime2 = new Date();
        ParkingTicket ticket2 = parkingLot.park(car, entryTime2, SlotType.MEDIUM, "GATE-2");
        System.out.println("Car parked - Ticket: " + ticket2.getTicketId() + 
                          ", Slot: " + ticket2.getSlotNumber());
        
        // Test 3: Check status
        Map<SlotType, Integer> status = parkingLot.status();
        System.out.println("\nParking Status:");
        System.out.println("Small slots available: " + status.get(SlotType.SMALL));
        System.out.println("Medium slots available: " + status.get(SlotType.MEDIUM));
        System.out.println("Large slots available: " + status.get(SlotType.LARGE));
        
        // Test 4: Exit bike after 2 hours
        Date exitTime1 = new Date(entryTime1.getTime() + 2 * 60 * 60 * 1000);
        double bill1 = parkingLot.exit(ticket1, exitTime1);
        System.out.println("\nBike exit - Bill: Rs. " + bill1);
        
        // Test 5: Park bike in medium slot
        Vehicle bike2 = new Vehicle("KA-03-9999", VehicleType.TWO_WHEELER);
        Date entryTime3 = new Date();
        ParkingTicket ticket3 = parkingLot.park(bike2, entryTime3, SlotType.MEDIUM, "GATE-1");
        System.out.println("\nBike parked in medium slot - Ticket: " + ticket3.getTicketId() + 
                          ", Slot: " + ticket3.getSlotNumber() + ", Type: " + ticket3.getSlotType());
        
        // Exit after 1 hour - should charge medium slot rate
        Date exitTime3 = new Date(entryTime3.getTime() + 1 * 60 * 60 * 1000);
        double bill3 = parkingLot.exit(ticket3, exitTime3);
        System.out.println("Bike exit from medium slot - Bill: Rs. " + bill3 + " (charged at medium rate)");
    }
}
