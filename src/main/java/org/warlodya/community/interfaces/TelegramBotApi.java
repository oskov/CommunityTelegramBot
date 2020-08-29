package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface TelegramBotApi {
    Optional<Message> sendMessage(String message, long id);
}
