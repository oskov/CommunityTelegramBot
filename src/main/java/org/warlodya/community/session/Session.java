package org.warlodya.community.session;

import org.warlodya.community.entities.BotUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * TODO add serialized json, for session params instead of state
 * Implement structure for session data
 */
@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @ManyToOne
    private BotUser relatedUser;
    @NotNull
    @Convert(converter = SessionStateConverter.class)
    private SessionState state;
    @NotNull
    private SessionType sessionType;
    @NotNull
    private LocalDateTime createdDt;
    @NotNull
    private LocalDateTime expirationDt;
    @Transient
    private Duration expiration;

    public Session(BotUser user, Duration expiration, SessionType sessionType, SessionState sessionState) {
        this.relatedUser = user;
        this.expiration = expiration;
        this.sessionType = sessionType;
        this.state = sessionState;
    }

    public Session() {
    }

    @PrePersist
    protected void onCreate() {
        createdDt = LocalDateTime.now();
        expirationDt = createdDt.plus(expiration);
    }

    public BotUser getRelatedUser() {
        return relatedUser;
    }

    public LocalDateTime getExpirationDt() {
        return expirationDt;
    }

    public SessionState getState() {
        return state;
    }

    public SessionType getSessionType() {
        return sessionType;
    }
}
