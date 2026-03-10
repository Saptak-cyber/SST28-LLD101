/**
 * Base contract for notification senders.
 * 
 * Preconditions: 
 * - Notification n may be null
 * - All notification fields may be null or empty (subtypes must handle gracefully)
 * 
 * Postconditions: 
 * - Attempts to send notification using channel-specific mechanism
 * - Logs outcome to audit (success or failure)
 * - Never throws exceptions for invalid input (handles errors gracefully)
 * 
 * Channel-specific behavior:
 * - Different channels may use different fields from Notification
 * - Channels must validate their required fields and handle failures gracefully
 * - All validation errors are reported via output, not exceptions
 */
public abstract class NotificationSender {
    protected final AuditLog audit;
    
    protected NotificationSender(AuditLog audit) { 
        this.audit = audit; 
    }
    
    /**
     * Sends a notification using the channel-specific mechanism.
     * Must handle all inputs gracefully without throwing exceptions.
     * 
     * @param n the notification to send (may be null)
     */
    public abstract void send(Notification n);
}
