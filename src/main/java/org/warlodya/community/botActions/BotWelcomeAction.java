package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.BotUserRelatedAction;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.util.UpdateUtils;

import java.util.Locale;

@Component
public class BotWelcomeAction implements BotUserRelatedAction {
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
    public void execute(Update update, BotUser botUser) {
        String languageCode = UpdateUtils.getLocale(update);
        String authorName = nameService.getAuthorName(botUser);
        String message = messageSource.getMessage("welcome.message", new String[]{authorName}, new Locale(languageCode));
        long chatId = UpdateUtils.getChatId(update);
        telegramBotApi.sendMessage(message, chatId);
    }

    @Override
    public boolean isAllowed(Update update) {
        return update.getMessage() != null && update.getMessage().getNewChatMembers().size() > 0;
    }
}
