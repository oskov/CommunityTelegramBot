package org.warlodya.community.botActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.warlodya.community.AbstractBotActionTest;
import org.warlodya.community.SavedUpdates;
import org.warlodya.community.botActions.addEvent.BotAddEventStartSessionAction;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.TelegramBotApi;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;
import org.warlodya.community.session.SessionCrudRepository;
import org.warlodya.community.session.SessionType;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

class BotAddEventStartSessionActionTest extends AbstractBotActionTest {
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
        final ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);

        BotUser botUser = mock(BotUser.class);
        BotAddEventStartSessionAction action = new BotAddEventStartSessionAction(telegramBotApi, sessionRepository, messageSource);
        BotRequest botRequest = createBotRequest(SavedUpdates.getUpdateWithMessage("/addEvent"), botUser, null);
        action.execute(botRequest);

        verify(sessionRepository).save(captor.capture());
        Session session = captor.getValue();

        assertSame(session.getSessionType(), SessionType.ADD_EVENT);
        assertSame(session.getRelatedUser(), botUser);
        assertTrue(session.getState().flags.get("addName"));
        verify(telegramBotApi, times(1)).sendMessage(nullable(String.class), anyLong());
    }

    @Test
    void isAllowed() {
        BotAddEventStartSessionAction action = new BotAddEventStartSessionAction(telegramBotApi, sessionRepository, messageSource);
        BotRequest botRequest = createBotRequest(SavedUpdates.getUpdateWithMessage("/addEvent"), null, null);
        assertTrue(action.isAllowed(botRequest));
    }
}