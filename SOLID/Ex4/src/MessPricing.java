public class MessPricing implements AddOnPricing {
    @Override
    public Money getMonthlyFee() {
        return new Money(1000.0);
    }
}
