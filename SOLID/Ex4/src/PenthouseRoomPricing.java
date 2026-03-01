public class PenthouseRoomPricing implements RoomPricing {
    @Override
    public Money getMonthlyFee() {
        return new Money(25000.0);
    }
}
