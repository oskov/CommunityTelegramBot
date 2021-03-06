package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.commands.EventsToStringCommand;
import org.warlodya.community.entities.Event;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.util.UpdateUtils;

import java.util.List;
import java.util.Locale;

@Component
public class BotListEventsAction implements BotAction {
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

    private String getMessage(List<Event> events, Update update) {
        var sb = new StringBuilder();
        var languageCode = UpdateUtils.getLocale(update);
        var headerMessage = messageSource.getMessage("events.header", null, new Locale(languageCode));
        sb.append(headerMessage).append('\n');
        var message = new EventsToStringCommand(
                events,
                false,
                UpdateUtils.getLocale(update),
                nameService,
                messageSource
        ).execute();
        sb.append(message);
        return sb.toString();
    }

    @Override
    public void execute(BotRequest botRequest) {
        var update = botRequest.getUpdate();
        var events = eventCrudRepository.findTop10ByOrderByEventDateAsc();
        telegramBotApi.sendMessage(getMessage(events, update), UpdateUtils.getChatId(update));
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        return UpdateUtils.isSingleCommand(botRequest.getUpdate(), "events");
    }
}
