package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.warlodya.community.entities.BotUser;

//TODO mb rename
public interface BotUserRelatedAction extends BotAbstractAction {
    void execute(Update update, BotUser botUser);
}
