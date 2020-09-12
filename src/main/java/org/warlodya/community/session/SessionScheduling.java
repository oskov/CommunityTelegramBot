package org.warlodya.community.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionScheduling {

    private SessionCrudRepository sessionCrudRepository;

    @Autowired
    public SessionScheduling(SessionCrudRepository sessionCrudRepository) {
        this.sessionCrudRepository = sessionCrudRepository;
    }

    // 5 minutes
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void removeExpiredSessions() {
        sessionCrudRepository.removeExpired(LocalDateTime.now());
    }
}
