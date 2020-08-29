package org.warlodya.community.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.warlodya.community.interfaces.TelegramAbsSender;
import org.warlodya.community.interfaces.TelegramBotApi;

import java.io.Serializable;
import java.util.Optional;

@Component
public class DefaultTelegramBotApi implements TelegramBotApi {
    private TelegramAbsSender telegramAbsSender;

    @Autowired
    public DefaultTelegramBotApi(TelegramAbsSender telegramAbsSender) {
        this.telegramAbsSender = telegramAbsSender;
    }

    @Override
    public Optional<Message> sendMessage(String message, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        return safeApiCall(sendMessage);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> safeApiCall(Method method) {
        try {
            return Optional.ofNullable(telegramAbsSender.execute(method));
        } catch (TelegramApiException e) {
            return Optional.empty();
        }
    }
}
