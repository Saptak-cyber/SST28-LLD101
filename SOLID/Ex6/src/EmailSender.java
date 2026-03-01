public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { 
        super(audit); 
    }

    @Override
    public void send(Notification n) {
        if (n.email == null || n.email.isEmpty()) {
            System.out.println("EMAIL ERROR: recipient email is missing");
            audit.add("email failed");
            return;
        }
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}
