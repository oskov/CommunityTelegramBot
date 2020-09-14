package org.warlodya.community;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.request.BotRequest;
import org.warlodya.community.session.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractBotActionTest {

    protected BotRequest createBotRequest(Update update, BotUser botUser, Session session) {
        BotRequest botRequest = mock(BotRequest.class);
        when(botRequest.getBotUser()).thenReturn(botUser);
        when(botRequest.getSession()).thenReturn(session);
        when(botRequest.getUpdate()).thenReturn(update);
        return botRequest;
    }
}
