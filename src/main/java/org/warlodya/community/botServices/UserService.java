package org.warlodya.community.botServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.repositories.UserCrudRepository;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.util.UpdateUtils;

import java.util.Optional;

@Component
public class UserService {
    private UserCrudRepository userRepository;
    private SessionCrudRepository sessionCrudRepository;

    @Autowired
    public UserService(UserCrudRepository userRepository, SessionCrudRepository sessionCrudRepository) {
        this.userRepository = userRepository;
        this.sessionCrudRepository = sessionCrudRepository;
    }

    public Optional<BotUser> findBotUser(User user) {
        BotUser userFromDb = userRepository.findByTelegramId(user.getId());
        return Optional.ofNullable(userFromDb);
    }

    public BotUser createBotUser(User user) {
        return userRepository.save(new BotUser(
                user.getId(),
                BotUser.UNKNOWN_PRIVATE_CHAT_ID,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getLanguageCode()));
    }

    // TODO cache
    public BotUser addTelegramUserFromUpdate(Update update) {
        User user = UpdateUtils.getUser(update);
        Optional<BotUser> userFromDb = findBotUser(user);
        BotUser botUser = userFromDb.orElseGet(() -> createBotUser(user));

        if (botUser.getPrivateChatId() == BotUser.UNKNOWN_PRIVATE_CHAT_ID && UpdateUtils.isPrivateMessage(update)) {
            botUser.setPrivateChatId(update.getMessage().getChatId());
            userRepository.save(botUser);
        }

        return botUser;
    }

    public Session getSessionForBotUser(BotUser botUser) {
        return sessionCrudRepository.findByRelatedUser(botUser);
    }
}
