package org.warlodya.community.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.warlodya.community.interfaces.TelegramAbsSender;

//TODO rename , should be more related to receiver class
@Component
public class DefaultTelegramAbsSenderBot extends DefaultAbsSender implements TelegramAbsSender {

    private final String botToken;

    @Autowired
    protected DefaultTelegramAbsSenderBot(@Value("${COMMUNITY_BOT_TOKEN}") String botToken,
                                          BotOptions options) {
        super((DefaultBotOptions) options); // TODO, fix cast
        this.botToken = botToken;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
