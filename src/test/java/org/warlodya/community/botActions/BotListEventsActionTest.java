package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.session.SessionCrudRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class BotListEventsActionTest {
    private TelegramBotApi telegramBotApi;
    private SessionCrudRepository sessionRepository;
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        telegramBotApi = mock(TelegramBotApi.class);
        sessionRepository = mock(SessionCrudRepository.class);
        messageSource = mock(MessageSource.class);
    }

    @Test
    void execute() {
    }


    @Test
    void isAllowed() {
        BotAddEventStartSessionAction action = new BotAddEventStartSessionAction(telegramBotApi, sessionRepository, messageSource);
        assertTrue(action.isAllowed(SavedUpdates.getUpdateWithMessage("/events")));
    }
}