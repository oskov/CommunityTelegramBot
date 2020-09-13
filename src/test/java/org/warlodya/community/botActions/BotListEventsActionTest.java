package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.interfaces.NameService;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class BotListEventsActionTest {
    private TelegramBotApi telegramBotApi;
    private EventCrudRepository eventCrudRepository;
    private MessageSource messageSource;
    private NameService nameService;

    @BeforeEach
    void setUp() {
        telegramBotApi = mock(TelegramBotApi.class);
        eventCrudRepository = mock(EventCrudRepository.class);
        messageSource = mock(MessageSource.class);
        nameService = mock(NameService.class);
    }

    @Test
    void execute() {
    }


    @Test
    void isAllowed() {
        BotListEventsAction action = new BotListEventsAction(telegramBotApi, messageSource, eventCrudRepository, nameService);
        assertTrue(action.isAllowed(SavedUpdates.getUpdateWithMessage("/events")));
    }
}