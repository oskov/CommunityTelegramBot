package org.warlodya.community.session;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.warlodya.community.entities.BotUser;

import java.time.LocalDateTime;

public interface SessionCrudRepository extends CrudRepository<Session, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM session WHERE expired_dt > :date")
    int removeExpired(@Param("date") LocalDateTime localDateTime);

    Session findByRelatedUser(BotUser botUser);
}
