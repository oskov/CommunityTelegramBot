package org.warlodya.community.request;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.session.Session;

public class BotRequest {
    private Update update;
    private BotUser botUser;
    private Session session;

    public BotRequest(Update update, BotUser botUser, Session session) {
        this.update = update;
        this.botUser = botUser;
        this.session = session;
    }

    public BotRequest(Update update, BotUser botUser) {
        this.update = update;
        this.botUser = botUser;
    }

    public BotRequest(Update update) {
        this.update = update;
    }

    public Update getUpdate() {
        return update;
    }

    public BotUser getBotUser() {
        return botUser;
    }

    public Session getSession() {
        return session;
    }

    public boolean hasBotUser() {
        return botUser != null;
    }

    public boolean hasSession() {
        return session != null;
    }
}
