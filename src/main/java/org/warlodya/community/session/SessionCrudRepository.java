package org.warlodya.community.session;

import org.springframework.data.repository.CrudRepository;
import org.warlodya.community.entities.BotUser;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface SessionCrudRepository extends CrudRepository<Session, Integer> {

    void deleteByExpirationDtBefore(@NotNull LocalDateTime expirationDt);

    Session findByRelatedUser(BotUser botUser);
}
