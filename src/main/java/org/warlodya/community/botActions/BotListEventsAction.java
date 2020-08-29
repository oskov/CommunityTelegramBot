package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.entities.Event;
import org.warlodya.community.interfaces.BotUserRelatedAction;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.util.UpdateUtils;

import java.util.Locale;

@Component
public class BotListEventsAction implements BotUserRelatedAction {

    private TelegramBotApi telegramBotApi;
    private EventCrudRepository eventCrudRepository;
    private MessageSource messageSource;
    private NameService nameService;

    @Autowired
    public BotListEventsAction(
            TelegramBotApi telegramBotApi,
            MessageSource messageSource,
            EventCrudRepository eventCrudRepository,
            NameService nameService
    ) {
        this.telegramBotApi = telegramBotApi;
        this.messageSource = messageSource;
        this.eventCrudRepository = eventCrudRepository;
        this.nameService = nameService;
    }

    @Override
    public void execute(Update update, BotUser botUser) {
        var events = eventCrudRepository.findAll();
        var sb = new StringBuilder();
        var languageCode = UpdateUtils.getLocale(update);
        var headerMessage = messageSource.getMessage("events.header", null, new Locale(languageCode));
        sb.append(headerMessage).append('\n');
        for (Event event : events) {
            String[] args = new String[]{
                    event.getEventName(),
                    event.getMessage(),
                    event.getCreatedDt().toString(),
                    nameService.getAuthorName(event.getCreator())};
            var eventMessage = messageSource.getMessage("events.list", args, new Locale(languageCode));
            sb.append('\n').append(eventMessage);
        }
        var result = sb.toString();
        telegramBotApi.sendMessage(result, UpdateUtils.getChatId(update));
    }

    @Override
    public boolean isAllowed(Update update) {
        return UpdateUtils.isSingleCommand(update, "events"); // TODO, some abstraction for single commands
    }
}
