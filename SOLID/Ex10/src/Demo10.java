public class Demo10 {
    public static void main(String[] args) {
        System.out.println("=== Transport Booking ===");
        
        // Dependency injection: create concrete implementations
        IDistanceCalculator distanceCalculator = new DistanceCalculator();
        IDriverAllocator driverAllocator = new DriverAllocator();
        IPaymentGateway paymentGateway = new PaymentGateway();
        IPricingService pricingService = new StandardPricingService();
        
        // Inject dependencies into service
        TransportBookingService svc = new TransportBookingService(
            distanceCalculator,
            driverAllocator,
            paymentGateway,
            pricingService
        );
        
        TripRequest req = new TripRequest("23BCS1010", new GeoPoint(12.97, 77.59), new GeoPoint(12.93, 77.62));
        svc.book(req);
    }
}
