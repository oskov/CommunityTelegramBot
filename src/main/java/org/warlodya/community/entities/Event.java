package org.warlodya.community.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int eventId;
    private Date eventDate;
    @ManyToOne
    @NotNull
    private BotUser creator;
    private String eventName;
    private String message;
    @NotNull
    private LocalDateTime createdDt;

    public Event(Date eventDate, BotUser creator, String eventName, String message) {
        this.eventDate = eventDate;
        this.creator = creator;
        this.eventName = eventName;
        this.message = message;
    }

    public Event() {
    }

    @PrePersist
    protected void onCreate() {
        createdDt = LocalDateTime.now();
    }

    public int getEventId() {
        return eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMessage() {
        return message;
    }

    public BotUser getCreator() {
        return creator;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }
}
