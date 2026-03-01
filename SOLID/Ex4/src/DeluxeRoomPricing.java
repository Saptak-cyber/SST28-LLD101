public class DeluxeRoomPricing implements RoomPricing {
    @Override
    public Money getMonthlyFee() {
        return new Money(16000.0);
    }
}
