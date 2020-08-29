package org.warlodya.community.util;

import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class UpdateUtils {
    public static Long getChatId(Update update) {
        return AbilityUtils.getChatId(update);
    }

    public static User getUser(Update update) {
        return AbilityUtils.getUser(update);
    }

    // TODO test, for different updates
    public static String getLocale(Update update) {
        return update.getMessage().getFrom().getLanguageCode();
    }

    public static Optional<String> getTextMessage(Update update) {
        if (update.hasMessage()) {
            return Optional.of(update.getMessage().getText());
        }
        return Optional.empty();
    }

    public static boolean isSingleCommand(Update update, String command) {
        var message = getTextMessage(update);
        if (message.isEmpty()) {
            return false;
        }
        return message.get().trim().equals('/' + command);
    }

    public static boolean isPrivateMessage(Update update) {
        return update.hasMessage() && update.getMessage().isUserMessage();
    }
}
