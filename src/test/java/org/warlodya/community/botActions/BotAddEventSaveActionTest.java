package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotAddEventSaveActionTest {
    private TelegramBotApi telegramBotApi;
    private SessionCrudRepository sessionRepository;
    private MessageSource messageSource;
    private EventCrudRepository eventCrudRepository;

    @BeforeEach
    void setUp() {
        telegramBotApi = mock(TelegramBotApi.class);
        sessionRepository = mock(SessionCrudRepository.class);
        messageSource = mock(MessageSource.class);
        eventCrudRepository = mock(EventCrudRepository.class);
    }

    @Test
    void execute() {
    }

    @Test
    void isAllowed() {
        Session session = mock(Session.class);
        SessionState sessionState = mock(SessionState.class);

        sessionState.flags = Map.of("addDate", true);
        when(session.getState()).thenReturn(sessionState);

        BotAddEventSaveAction action = new BotAddEventSaveAction(telegramBotApi, sessionRepository, eventCrudRepository, messageSource);

        action.isAllowed(SavedUpdates.getUpdateWithMessage(""), session);
    }
}