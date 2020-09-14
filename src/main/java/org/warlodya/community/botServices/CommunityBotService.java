package org.warlodya.community.botServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.BotRequestConsumer;
import org.warlodya.community.request.BotRequest;

import java.util.List;

@Component
public class CommunityBotService implements BotRequestConsumer {
    private List<BotAction> botActions;

    @Autowired
    public CommunityBotService(
            List<BotAction> botActions
    ) {
        this.botActions = botActions;
    }

    private void sendUpdatesToActions(BotRequest botRequest) {
        botActions.stream()
                .filter(botAction -> botAction.isAllowed(botRequest))
                .forEach(botAction -> botAction.execute(botRequest));
    }

    @Override
    public void consumeBotRequest(BotRequest botRequest) {
        sendUpdatesToActions(botRequest);
    }
}
