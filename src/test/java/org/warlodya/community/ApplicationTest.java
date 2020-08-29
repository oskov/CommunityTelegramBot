package org.warlodya.community;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.generics.BotSession;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(properties = {
        "MYSQL_DATABASE = foo",
        "MYSQL_USER = foo",
        "MYSQL_PASSWORD = foo",
})
class ApplicationTest {

    @BeforeAll
    static void setUp() {
        ApiContext.register(BotSession.class, mock(BotSession.class).getClass());
    }

    @Test
    void contextLoads() {
    }

    @Configuration
    static class TestConfig {
        @Bean
        DataSource userRepository() {
            return mock(DataSource.class);
        }
    }
}