public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { 
        super(audit); 
    }

    @Override
    public void send(Notification n) {
        if (n.phone == null || n.phone.isEmpty()) {
            System.out.println("SMS ERROR: recipient phone is missing");
            audit.add("sms failed");
            return;
        }
        // SMS channel uses phone and body (subject not applicable to SMS protocol)
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
