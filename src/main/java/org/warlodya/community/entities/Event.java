package org.warlodya.community.entities;

import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @Unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int eventId;
    private LocalDateTime eventDate;
    @ManyToOne
    @NotNull
    private BotUser creator;
    private String eventName;
    private String message;
    private boolean isDeleted = false;
    @NotNull
    private LocalDateTime createdDt;

    public Event(LocalDateTime eventDate, BotUser creator, String eventName, String message) {
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

    public LocalDateTime getEventDate() {
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

    public boolean isDeleted() {
        return isDeleted;
    }
}
