package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdateRelatedAction extends BotAbstractAction {
    void execute(Update update);
}
