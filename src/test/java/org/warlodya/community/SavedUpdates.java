package org.warlodya.community;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SavedUpdates {

    public static Update getUpdateWithMessage(String text) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(user.getLanguageCode()).thenReturn("en");

        when(message.getText()).thenReturn(text);
        when(message.hasText()).thenReturn(true);
        when(message.getChatId()).thenReturn(1L);
        when(message.getFrom()).thenReturn(user);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);

        return update;
    }
}
