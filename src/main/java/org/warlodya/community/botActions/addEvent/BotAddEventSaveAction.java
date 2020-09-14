package org.warlodya.community.botActions.addEvent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.entities.Event;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionType;
import org.warlodya.community.util.RegexPatterns;
import org.warlodya.community.util.UpdateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
public class BotAddEventSaveAction implements BotAction {
    private SessionCrudRepository sessionCrudRepository;
    private EventCrudRepository eventCrudRepository;
    private TelegramBotApi telegramBotApi;
    private MessageSource messageSource;

    @Autowired
    public BotAddEventSaveAction(
            TelegramBotApi telegramBotApi,
            SessionCrudRepository sessionCrudRepository,
            EventCrudRepository eventCrudRepository,
            MessageSource messageSource
    ) {
        this.telegramBotApi = telegramBotApi;
        this.sessionCrudRepository = sessionCrudRepository;
        this.eventCrudRepository = eventCrudRepository;
        this.messageSource = messageSource;
    }

    private Event createNewEvent(LocalDateTime dateTime, BotUser botUser, Session session) {
        var eventName = session.getState().data.get("name");
        var eventDescription = session.getState().data.get("description");
        return new Event(dateTime, botUser, eventName, eventDescription);
    }

    private Optional<LocalDateTime> getDateFromString(String dateString) {
        if (dateString.matches(RegexPatterns.MYSQL_DATE_REGEX)) {
            return Optional.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        }
        if (dateString.matches(RegexPatterns.MYSQL_DATE_TIME_PATTERN_REGEX)) {
            return Optional.of(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        return Optional.empty();
    }

    private String[] getErrorMessageData() {
        var dateTime = LocalDateTime.now();
        var stringOfDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var stringOfDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return new String[]{stringOfDate, stringOfDateTime};
    }

    private void sendErrorMessageToUser(Update update) {
        String languageCode = UpdateUtils.getLocale(update);
        String message = messageSource.getMessage("common.errors.date", getErrorMessageData(), new Locale(languageCode));
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    private void sendMessageToUser(Update update, String eventName) {
        String languageCode = UpdateUtils.getLocale(update);
        String message = messageSource.getMessage("events.save.completeSave", new String[]{eventName}, new Locale(languageCode));
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    @Override
    public void execute(BotRequest botRequest) {
        var botUser = botRequest.getBotUser();
        var session = botRequest.getSession();
        var update = botRequest.getUpdate();
        var dateTime = getDateFromString(update.getMessage().getText());
        if (dateTime.isEmpty()) {
            sendErrorMessageToUser(update);
            return;
        }
        var event = createNewEvent(dateTime.get(), botUser, session);
        eventCrudRepository.save(event);
        sessionCrudRepository.delete(session);
        sendMessageToUser(update, event.getEventName());
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        var session = botRequest.getSession();
        var update = botRequest.getUpdate();
        boolean shouldAddDate = session.getState().flags.getOrDefault("addDate", false);
        return UpdateUtils.hasText(update) && session.getSessionType() == SessionType.ADD_EVENT && shouldAddDate;
    }
}
