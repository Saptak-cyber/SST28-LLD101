package com.example.tickets;

/**
 * Service layer that creates tickets using immutable Builder pattern.
 * No longer mutates tickets after creation - returns new instances instead.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        return IncidentTicket.builder()
            .id(id)
            .reporterEmail(reporterEmail)
            .title(title)
            .priority("MEDIUM")
            .source("CLI")
            .customerVisible(false)
            .addTag("NEW")
            .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket) {
        return ticket.toBuilder()
            .priority("CRITICAL")
            .addTag("ESCALATED")
            .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {
        return ticket.toBuilder()
            .assigneeEmail(assigneeEmail)
            .build();
    }
}
