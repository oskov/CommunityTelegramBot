package org.warlodya.community.botServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.interfaces.*;

import java.util.List;

@Component
public class CommunityBotService implements UpdateConsumer {

    private UserService userService;
    private List<BotAbstractAction> botActions;

    @Autowired
    public CommunityBotService(UserService userService, List<BotAbstractAction> botActions) {
        this.userService = userService;
        this.botActions = botActions;
    }

    @Override
    public boolean consumeUpdate(Update update) {
        return sendUpdatesToActions(update);
    }

    private boolean sendUpdatesToActions(Update update) {
        var user = userService.addTelegramUserFromUpdate(update);
        botActions.stream()
                .filter(botAction -> botAction.isAllowed(update))
                .forEach(botAction -> {
                    if (botAction instanceof BotUpdateRelatedAction action) {
                        action.execute(update);
                    }
                    if (botAction instanceof BotUserRelatedAction action) {
                        action.execute(update, user);
                    }
                    if (botAction instanceof BotSimpleAction action) {
                        action.execute();
                    }
                });
        return true; // TODO
    }
}
