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
    private List<BotInSessionAction> botInSessionActions;

    @Autowired
    public CommunityBotService(
            UserService userService,
            List<BotAbstractAction> botActions,
            List<BotInSessionAction> botInSessionActions
    ) {
        this.userService = userService;
        this.botActions = botActions;
        this.botInSessionActions = botInSessionActions;
    }

    @Override
    public boolean consumeUpdate(Update update) {
        return sendUpdatesToActions(update);
    }

    private boolean sendUpdatesToActions(Update update) {
        var botUser = userService.addTelegramUserFromUpdate(update);
        var session = userService.getSessionForBotUser(botUser);
        if (session.isPresent()) {
            botInSessionActions.stream()
                    .filter(botInSessionAction -> botInSessionAction.isAllowed(update, session.get()))
                    .forEach(botInSessionAction -> botInSessionAction.execute(update, botUser, session.get()));
        } else {
            botActions.stream()
                    .filter(botAction -> botAction.isAllowed(update))
                    .forEach(botAction -> {
                        if (botAction instanceof BotUpdateRelatedAction action) {
                            action.execute(update);
                        }
                        if (botAction instanceof BotUserRelatedAction action) {
                            action.execute(update, botUser);
                        }
                        if (botAction instanceof BotSimpleAction action) {
                            action.execute();
                        }
                    });
        }
        return true; // TODO
    }
}
