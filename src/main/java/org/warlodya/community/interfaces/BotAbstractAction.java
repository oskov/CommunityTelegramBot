package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotAbstractAction {

    //TODO: rename
    boolean isAllowed(Update update);
}
