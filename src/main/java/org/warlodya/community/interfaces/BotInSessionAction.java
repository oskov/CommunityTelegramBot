package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.session.Session;

/**
 * TODO refactor sessions and session usage
 */
public interface BotInSessionAction {
    void execute(Update update, BotUser botUser, Session session);

    //TODO: rename
    boolean isAllowed(Update update, Session session);
}
