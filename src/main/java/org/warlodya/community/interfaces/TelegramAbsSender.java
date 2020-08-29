package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;

public interface TelegramAbsSender {

    <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException;

    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;

}
