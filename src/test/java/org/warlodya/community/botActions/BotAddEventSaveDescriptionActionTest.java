package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.warlodya.community.AbstractBotActionTest;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.botActions.addEvent.BotAddEventSaveDescriptionAction;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;
import org.warlodya.community.session.SessionType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BotAddEventSaveDescriptionActionTest extends AbstractBotActionTest {
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
        BotAddEventSaveDescriptionAction action = new BotAddEventSaveDescriptionAction(telegramBotApi, sessionRepository, messageSource);

        BotRequest botRequest = createBotRequest(SavedUpdates.getUpdateWithMessage("testDesc"), botUser, session);

        action.execute(botRequest);

        verify(sessionRepository).save(captor.capture());
        Session session = captor.getValue();

        assertEquals(session.getState().data.get("description"), "testDesc");
        assertTrue(session.getState().flags.get("addDate"));
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void isAllowed() {
        SessionState sessionState = mock(SessionState.class);

        sessionState.flags = Map.of("addDescription", true);
        when(session.getState()).thenReturn(sessionState);

        when(session.getSessionType()).thenReturn(SessionType.ADD_EVENT);

        BotAddEventSaveDescriptionAction action = new BotAddEventSaveDescriptionAction(telegramBotApi, sessionRepository, messageSource);
        BotRequest botRequest = createBotRequest(SavedUpdates.getUpdateWithMessage(""), null, session);

        assertTrue(action.isAllowed(botRequest));
    }
}