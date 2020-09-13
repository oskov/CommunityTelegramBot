package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;
import org.warlodya.community.session.SessionType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BotAddEventSaveNameActionTest {
    private TelegramBotApi telegramBotApi;
    private SessionCrudRepository sessionRepository;
    private MessageSource messageSource;
    private Session session;
    @BeforeEach
    void setUp() {
        telegramBotApi = mock(TelegramBotApi.class);
        sessionRepository = mock(SessionCrudRepository.class);
        messageSource = mock(MessageSource.class);
        session = mock(Session.class);
    }

    @Test
    void execute() {
        final ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);

        SessionState sessionState = new SessionState();
        when(session.getState()).thenReturn(sessionState);

        BotUser botUser = mock(BotUser.class);
        BotAddEventSaveNameAction action = new BotAddEventSaveNameAction(telegramBotApi, sessionRepository, messageSource);
        action.execute(SavedUpdates.getUpdateWithMessage("testName"), botUser, session);

        verify(sessionRepository).save(captor.capture());
        Session session = captor.getValue();

        assertEquals(session.getState().data.get("name"), "testName");
        assertTrue(session.getState().flags.get("addDescription"));
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void isAllowed() {
        SessionState sessionState = mock(SessionState.class);

        sessionState.flags = Map.of("addName", true);
        when(session.getState()).thenReturn(sessionState);
        when(session.getSessionType()).thenReturn(SessionType.ADD_EVENT);

        BotAddEventSaveNameAction action = new BotAddEventSaveNameAction(telegramBotApi, sessionRepository, messageSource);

        assertTrue(action.isAllowed(SavedUpdates.getUpdateWithMessage(""), session));
    }
}