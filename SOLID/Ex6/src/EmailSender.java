public class EmailSender extends NotificationSender {
    private final NotificationValidator validator;

    public EmailSender(AuditLog audit) { 
        super(audit); 
        this.validator = new NotificationValidator();
    }

    @Override
    public void send(Notification n) {
        NotificationValidator.ValidationResult result = validator.validateForEmail(n);
        if (!result.isValid) {
            System.out.println("EMAIL ERROR: " + result.errorMessage);
            audit.add("email failed");
            return;
        }
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}
