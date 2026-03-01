public interface RoomPricing extends PricingComponent {
    static RoomPricing forRoomType(int roomType) {
        return switch (roomType) {
            case LegacyRoomTypes.SINGLE -> new SingleRoomPricing();
            case LegacyRoomTypes.DOUBLE -> new DoubleRoomPricing();
            case LegacyRoomTypes.TRIPLE -> new TripleRoomPricing();
            default -> new DeluxeRoomPricing();
        };
    }
}
