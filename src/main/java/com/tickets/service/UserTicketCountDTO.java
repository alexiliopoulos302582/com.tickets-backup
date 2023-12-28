package com.tickets.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_ticket_counts")
public class UserTicketCountDTO {

    @Id
    @Column(name = "assigned_to")
    private String assignedTo;
    @Column(name = "open_tickets_count")
    private long openTicketsCount;
    @Column(name = "closed_tickets_count")
    private long closedTicketsCount;

    public UserTicketCountDTO(String assignedTo, long openTicketsCount, long closedTicketsCount) {
        this.assignedTo = assignedTo;
        this.openTicketsCount = openTicketsCount;
        this.closedTicketsCount = closedTicketsCount;
    }

    public UserTicketCountDTO() {
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public long getOpenTicketsCount() {
        return openTicketsCount;
    }

    public void setOpenTicketsCount(long openTicketsCount) {
        this.openTicketsCount = openTicketsCount;
    }

    public long getClosedTicketsCount() {
        return closedTicketsCount;
    }

    public void setClosedTicketsCount(long closedTicketsCount) {
        this.closedTicketsCount = closedTicketsCount;
    }
}
