package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.interfaces.BotUpdateRelatedAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.util.UpdateUtils;

@Component
public class BotInfoAction implements BotUpdateRelatedAction {

    private TelegramBotApi telegramBotApi;

    @Autowired
    public BotInfoAction(TelegramBotApi telegramBotApi) {
        this.telegramBotApi = telegramBotApi;
    }

    @Override
    public void execute(Update update) {
        telegramBotApi.sendMessage("TBA =)", UpdateUtils.getChatId(update)); // TODO implement
    }

    @Override
    public boolean isAllowed(Update update) {
        return UpdateUtils.isSingleCommand(update, "info"); // TODO, some abstraction for single commands
    }
}
