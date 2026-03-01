import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demo showing immutability in action.
 * 
 * After refactor:
 * - direct mutation does not compile (no setters)
 * - external modifications to tags have no effect on the ticket
 * - service "updates" return NEW ticket instances
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // Service methods now return NEW instances instead of mutating
        IncidentTicket t2 = service.assign(t, "agent@example.com");
        System.out.println("\nAfter assign (new instance): " + t2);
        System.out.println("Original unchanged: " + t);

        IncidentTicket t3 = service.escalateToCritical(t2);
        System.out.println("\nAfter escalate (new instance): " + t3);
        System.out.println("Previous unchanged: " + t2);

        // Demonstrate that external mutation has no effect (tags are unmodifiable)
        List<String> tags = t3.getTags();
        System.out.println("\nAttempting to modify tags externally...");
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("ERROR: Should have thrown exception!");
        } catch (UnsupportedOperationException e) {
            System.out.println("SUCCESS: Tags are immutable - " + e.getClass().getSimpleName());
        }
        System.out.println("Ticket unchanged: " + t3);

        // Demonstrate builder pattern
        System.out.println("\n--- Builder Pattern Demo ---");
        IncidentTicket customTicket = IncidentTicket.builder()
            .id("TCK-2000")
            .reporterEmail("user@example.com")
            .title("Custom ticket with all fields")
            .description("This is a detailed description")
            .priority("HIGH")
            .addTag("CUSTOM")
            .addTag("DEMO")
            .assigneeEmail("support@example.com")
            .customerVisible(true)
            .slaMinutes(120)
            .source("WEBHOOK")
            .build();
        System.out.println("Custom ticket: " + customTicket);
    }
}
