package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.util.UpdateUtils;

import java.util.Locale;

@Component
public class BotWelcomeAction implements BotAction {
    private MessageSource messageSource;
    private NameService nameService;
    private TelegramBotApi telegramBotApi;

    @Autowired
    public BotWelcomeAction(TelegramBotApi telegramBotApi, MessageSource messageSource, NameService nameService) {
        this.telegramBotApi = telegramBotApi;
        this.messageSource = messageSource;
        this.nameService = nameService;
    }

    @Override
    public void execute(BotRequest botRequest) {
        var update = botRequest.getUpdate();
        var botUser = botRequest.getBotUser();
        var languageCode = UpdateUtils.getLocale(update);
        var authorName = nameService.getAuthorName(botUser);
        var message = messageSource.getMessage("welcome.message", new String[]{authorName}, new Locale(languageCode));
        var chatId = UpdateUtils.getChatId(update);
        telegramBotApi.sendMessage(message, chatId);
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        var update = botRequest.getUpdate();
        return update.getMessage() != null && update.getMessage().getNewChatMembers().size() > 0;
    }
}
