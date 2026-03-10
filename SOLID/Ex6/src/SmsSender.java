public class SmsSender extends NotificationSender {
    private final NotificationValidator validator;

    public SmsSender(AuditLog audit) { 
        super(audit); 
        this.validator = new NotificationValidator();
    }

    @Override
    public void send(Notification n) {
        NotificationValidator.ValidationResult result = validator.validateForSms(n);
        if (!result.isValid) {
            System.out.println("SMS ERROR: " + result.errorMessage);
            audit.add("sms failed");
            return;
        }
        // SMS channel uses phone and body (subject not applicable to SMS protocol)
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
