package org.warlodya.community.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.warlodya.community.interfaces.UpdateConsumer;

import java.util.List;

//TODO rename , should be more related to sender class
@Component
public class GameCommunityBot implements LongPollingBot {
    // TODO logging in project
    public static final String BOT_USERNAME = "GameCommunityBot";

    private final String botToken;
    private final BotOptions botOptions;

    private UpdateConsumer updateConsumer;

    @Autowired
    public GameCommunityBot(@Value("${GAME_COMMUNITY_BOT_TOKEN}") String botToken,
                            BotOptions botOptions,
                            UpdateConsumer updateConsumer) {
        this.botToken = botToken;
        this.botOptions = botOptions;
        this.updateConsumer = updateConsumer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateConsumer.consumeUpdate(update);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotOptions getOptions() {
        return botOptions;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        //TODO
    }
}
