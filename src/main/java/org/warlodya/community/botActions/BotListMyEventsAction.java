package org.warlodya.community.botActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.warlodya.community.commands.EventsToStringCommand;
import org.warlodya.community.interfaces.BotAction;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.util.UpdateUtils;

@Component
public class BotListMyEventsAction implements BotAction {
    private TelegramBotApi telegramBotApi;
    private EventCrudRepository eventCrudRepository;
    private NameService nameService;
    private MessageSource messageSource;

    @Autowired
    public BotListMyEventsAction(
            TelegramBotApi telegramBotApi,
            EventCrudRepository eventCrudRepository,
            NameService nameService,
            MessageSource messageSource
    ) {
        this.telegramBotApi = telegramBotApi;
        this.eventCrudRepository = eventCrudRepository;
        this.nameService = nameService;
        this.messageSource = messageSource;
    }

    @Override
    public void execute(BotRequest botRequest) {
        var update = botRequest.getUpdate();
        var botUser = botRequest.getBotUser();
        var events = eventCrudRepository.findByCreator(botUser);
        var message = new EventsToStringCommand(
                events,
                true,
                UpdateUtils.getLocale(update),
                nameService,
                messageSource
        ).execute();
        telegramBotApi.sendMessage(message, UpdateUtils.getChatId(update));
    }

    @Override
    public boolean isAllowed(BotRequest botRequest) {
        return UpdateUtils.isSingleCommand(botRequest.getUpdate(), "myEvents");
    }
}
