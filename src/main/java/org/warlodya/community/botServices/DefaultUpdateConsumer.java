package org.warlodya.community.botServices;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.interfaces.BotRequestConsumer;
import org.warlodya.community.interfaces.UpdateConsumer;
import org.warlodya.community.request.BotRequest;

@Component
public class DefaultUpdateConsumer implements UpdateConsumer {
    private UserService userService;
    private BotRequestConsumer botRequestConsumer;

    public DefaultUpdateConsumer(UserService userService, BotRequestConsumer botRequestConsumer) {
        this.userService = userService;
        this.botRequestConsumer = botRequestConsumer;
    }

    @Override
    public void consumeUpdate(Update update) {
        var botUser = userService.addTelegramUserFromUpdate(update);
        var session = userService.getSessionForBotUser(botUser);
        botRequestConsumer.consumeBotRequest(new BotRequest(update, botUser, session));
    }
}
