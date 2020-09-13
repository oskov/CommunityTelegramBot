package org.warlodya.community.botActions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.interfaces.BotUpdateRelatedAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.util.UpdateUtils;

@Component
public class BotDebugAction implements BotUpdateRelatedAction {

    private TelegramBotApi telegramBotApi;

    public BotDebugAction(TelegramBotApi telegramBotApi) {
        this.telegramBotApi = telegramBotApi;
    }

    @Override
    public void execute(Update update) {
        telegramBotApi.sendMessage(update.toString(), UpdateUtils.getChatId(update));
    }

    @Override
    public boolean isAllowed(Update update) {
        return false;
    }
}
