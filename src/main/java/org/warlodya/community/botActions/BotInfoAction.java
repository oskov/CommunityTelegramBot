package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.util.UpdateUtils;

@Component
public class BotInfoAction implements BotAction {
    private TelegramBotApi telegramBotApi;

    @Autowired
    public BotInfoAction(TelegramBotApi telegramBotApi) {
        this.telegramBotApi = telegramBotApi;
    }

    @Override
    public void execute(BotRequest botRequest) {
        telegramBotApi.sendMessage("TBA =)", UpdateUtils.getChatId(botRequest.getUpdate())); // TODO implement
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        return UpdateUtils.isSingleCommand(botRequest.getUpdate(), "info");
    }
}
