public class WhatsAppSender extends NotificationSender {
    private final NotificationValidator validator;
    
    public WhatsAppSender(AuditLog audit) { 
        super(audit);
        this.validator = new NotificationValidator();
    }

    @Override
    public void send(Notification n) {
        // Validation separated from sending logic
        NotificationValidator.ValidationResult result = validator.validateForWhatsApp(n);
        if (!result.isValid) {
            System.out.println("WA ERROR: " + result.errorMessage);
            audit.add("WA failed");
            return;
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}
