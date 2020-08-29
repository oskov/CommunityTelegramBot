package org.warlodya.community.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.generics.BotOptions;

@Configuration
public class BotConfiguration {
    @Bean
    public BotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }
}
