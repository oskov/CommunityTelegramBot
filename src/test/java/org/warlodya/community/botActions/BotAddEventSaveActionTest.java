package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.entities.Event;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.repositories.EventCrudRepository;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionState;
import org.warlodya.community.session.SessionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BotAddEventSaveActionTest {
    private TelegramBotApi telegramBotApi;
    private SessionCrudRepository sessionRepository;
    private MessageSource messageSource;
    private EventCrudRepository eventCrudRepository;
    private Session session;

    @BeforeEach
    void setUp() {
        telegramBotApi = mock(TelegramBotApi.class);
        sessionRepository = mock(SessionCrudRepository.class);
        messageSource = mock(MessageSource.class);
        eventCrudRepository = mock(EventCrudRepository.class);
        session = mock(Session.class);
    }

    @Test
    void executeDate() {
        final ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);

        SessionState sessionState = new SessionState();
        sessionState.data.put("name", "testName");
        sessionState.data.put("description", "testDescription");
        when(session.getState()).thenReturn(sessionState);

        BotUser botUser = mock(BotUser.class);
        BotAddEventSaveAction action = new BotAddEventSaveAction(telegramBotApi, sessionRepository, eventCrudRepository, messageSource);
        action.execute(SavedUpdates.getUpdateWithMessage("2020-02-20"), botUser, session);

        verify(sessionRepository, times(1)).delete(session);
        verify(eventCrudRepository).save(captor.capture());
        Event event = captor.getValue();
        assertEquals(event.getEventDate(), LocalDate.parse("2020-02-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        assertEquals(event.getEventName(), "testName");
        assertEquals(event.getMessage(), "testDescription");
        assertEquals(event.getCreator(), botUser);
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void executeDateTime() {
        final ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);

        SessionState sessionState = new SessionState();
        sessionState.data.put("name", "testName");
        sessionState.data.put("description", "testDescription");
        when(session.getState()).thenReturn(sessionState);

        BotUser botUser = mock(BotUser.class);
        BotAddEventSaveAction action = new BotAddEventSaveAction(telegramBotApi, sessionRepository, eventCrudRepository, messageSource);
        action.execute(SavedUpdates.getUpdateWithMessage("2020-02-20 04:20"), botUser, session);

        verify(sessionRepository, times(1)).delete(session);
        verify(eventCrudRepository).save(captor.capture());
        Event event = captor.getValue();
        assertEquals(event.getEventDate(), LocalDateTime.parse("2020-02-20 04:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(event.getEventName(), "testName");
        assertEquals(event.getMessage(), "testDescription");
        assertEquals(event.getCreator(), botUser);
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void executeErrorDate() {
        final ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);

        SessionState sessionState = new SessionState();
        sessionState.data.put("name", "testName");
        sessionState.data.put("description", "testDescription");
        when(session.getState()).thenReturn(sessionState);

        BotUser botUser = mock(BotUser.class);
        BotAddEventSaveAction action = new BotAddEventSaveAction(telegramBotApi, sessionRepository, eventCrudRepository, messageSource);
        action.execute(SavedUpdates.getUpdateWithMessage("not date"), botUser, session);

        verify(sessionRepository, times(0)).delete(session);
        verify(eventCrudRepository, times(0)).save(notNull());
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void isAllowed() {
        SessionState sessionState = mock(SessionState.class);

        sessionState.flags = Map.of("addDate", true);
        when(session.getState()).thenReturn(sessionState);

        when(session.getSessionType()).thenReturn(SessionType.ADD_EVENT);

        BotAddEventSaveAction action = new BotAddEventSaveAction(telegramBotApi, sessionRepository, eventCrudRepository, messageSource);

        action.isAllowed(SavedUpdates.getUpdateWithMessage(""), session);
    }
}