package org.warlodya.community.repositories;

import org.springframework.data.repository.CrudRepository;
import org.warlodya.community.entities.BotUser;

public interface UserCrudRepository extends CrudRepository<BotUser, Integer> {
    BotUser findByTelegramId(int telegramId);
}
