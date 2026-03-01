/**
 * Validates and normalizes notification data before sending.
 * Separates validation responsibility from sending logic.
 */
public class NotificationValidator {
    
    public static class ValidationResult {
        public final boolean isValid;
        public final String errorMessage;
        
        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }
        
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }
        
        public static ValidationResult failure(String message) {
            return new ValidationResult(false, message);
        }
    }
    
    public ValidationResult validateForWhatsApp(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            return ValidationResult.failure("phone must start with + and country code");
        }
        return ValidationResult.success();
    }
}
