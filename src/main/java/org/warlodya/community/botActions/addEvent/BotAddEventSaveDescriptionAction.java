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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class BotAddEventSaveDescriptionAction implements BotAction {
    private SessionCrudRepository sessionCrudRepository;
    private TelegramBotApi telegramBotApi;
    private MessageSource messageSource;

    @Autowired
    public BotAddEventSaveDescriptionAction(
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
        state.flags.put("addDescription", false);
        state.flags.put("addDate", true);
        state.data.put("description", update.getMessage().getText());
        sessionCrudRepository.save(session);
    }

    private String[] getMessageData() {
        var dateTime = LocalDateTime.now();
        var stringOfDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return new String[]{stringOfDateTime};
    }

    private void sendMessageToUser(Update update) {
        var languageCode = UpdateUtils.getLocale(update);
        var message = messageSource.getMessage("events.save.addDate", getMessageData(), new Locale(languageCode));
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    @Override
    public void execute(BotRequest botRequest) {
        var session = botRequest.getSession();
        var update = botRequest.getUpdate();
        changeSessionState(update, session);
        sendMessageToUser(update);
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        var session = botRequest.getSession();
        var update = botRequest.getUpdate();
        boolean shouldAddDescription = session.getState().flags.getOrDefault("addDescription", false);
        return UpdateUtils.hasText(update) && session.getSessionType() == SessionType.ADD_EVENT && shouldAddDescription;
    }
}
