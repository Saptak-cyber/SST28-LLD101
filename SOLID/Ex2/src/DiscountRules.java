public interface DiscountRules {
    double calculateDiscount(String customerType, double subtotal, int distinctLines);
}
