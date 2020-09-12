package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotAddEventSaveNameActionTest {
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
        Session session = mock(Session.class);
        SessionState sessionState = mock(SessionState.class);

        sessionState.flags = Map.of("addName", true);
        when(session.getState()).thenReturn(sessionState);

        BotAddEventSaveNameAction action = new BotAddEventSaveNameAction(telegramBotApi, sessionRepository, messageSource);

        assertTrue(action.isAllowed(SavedUpdates.getUpdateWithMessage(""), session));
    }
}