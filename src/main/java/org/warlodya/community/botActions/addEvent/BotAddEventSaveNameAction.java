package org.warlodya.community.botActions.addEvent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionType;
import org.warlodya.community.util.UpdateUtils;

import java.util.Locale;

@Component
public class BotAddEventSaveNameAction implements BotAction {
    private SessionCrudRepository sessionCrudRepository;
    private TelegramBotApi telegramBotApi;
    private MessageSource messageSource;

    @Autowired
    public BotAddEventSaveNameAction(
            TelegramBotApi telegramBotApi,
            SessionCrudRepository sessionCrudRepository,
            MessageSource messageSource
    ) {
        this.telegramBotApi = telegramBotApi;
        this.sessionCrudRepository = sessionCrudRepository;
        this.messageSource = messageSource;
    }

    private void changeSessionState(Update update, Session session) {
        var state = session.getState();
        state.flags.put("addName", false);
        state.flags.put("addDescription", true);
        state.data.put("name", update.getMessage().getText());
        sessionCrudRepository.save(session);
    }

    private void sendMessageToUser(Update update) {
        String languageCode = UpdateUtils.getLocale(update);
        String message = messageSource.getMessage("events.save.addDescription", null, new Locale(languageCode));
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    @Override
    public void execute(BotRequest botRequest) {
        var update = botRequest.getUpdate();
        var session = botRequest.getSession();
        changeSessionState(update, session);
        sendMessageToUser(update);
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        var session = botRequest.getSession();
        var update = botRequest.getUpdate();
        boolean shouldAddName = session.getState().flags.getOrDefault("addName", false);
        return UpdateUtils.hasText(update) && session.getSessionType() == SessionType.ADD_EVENT && shouldAddName;
    }
}
