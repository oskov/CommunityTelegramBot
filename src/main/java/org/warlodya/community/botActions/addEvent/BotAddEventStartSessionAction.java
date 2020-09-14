package org.warlodya.community.botActions.addEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;
import org.warlodya.community.session.SessionType;
import org.warlodya.community.util.Const;
import org.warlodya.community.util.UpdateUtils;

import java.util.Locale;

@Component
public class BotAddEventStartSessionAction implements BotAction {
    private SessionCrudRepository sessionCrudRepository;
    private TelegramBotApi telegramBotApi;
    private MessageSource messageSource;

    @Autowired
    public BotAddEventStartSessionAction(
            TelegramBotApi telegramBotApi,
            SessionCrudRepository sessionCrudRepository,
            MessageSource messageSource
    ) {
        this.telegramBotApi = telegramBotApi;
        this.sessionCrudRepository = sessionCrudRepository;
        this.messageSource = messageSource;
    }

    private void createNewSession(BotUser botUser) {
        var sessionState = new SessionState();
        sessionState.flags.put("addName", true);
        var session = new Session(botUser, Const.SESSION_DURATION, SessionType.ADD_EVENT, sessionState);
        sessionCrudRepository.save(session);
    }

    private void sendMessageToUser(Update update) {
        String languageCode = UpdateUtils.getLocale(update);
        String message = messageSource.getMessage("events.save.addName", null, new Locale(languageCode));
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    @Override
    public void execute(BotRequest botRequest) {
        createNewSession(botRequest.getBotUser());
        sendMessageToUser(botRequest.getUpdate());
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        return UpdateUtils.isSingleCommand(botRequest.getUpdate(), "addEvent");
    }
}
