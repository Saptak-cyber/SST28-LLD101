public class ParkingPricing implements AddOnPricing {
    @Override
    public Money getMonthlyFee() {
        return new Money(800.0);
    }
}
