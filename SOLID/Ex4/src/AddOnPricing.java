public interface AddOnPricing extends PricingComponent {
    static AddOnPricing forAddOn(AddOn addOn) {
        return switch (addOn) {
            case MESS -> new MessPricing();
            case LAUNDRY -> new LaundryPricing();
            case GYM -> new GymPricing();
        };
    }
}
