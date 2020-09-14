package org.warlodya.community.interfaces;

import org.warlodya.community.request.BotRequest;

public interface BotAction {
    public void execute(BotRequest botRequest);

    //TODO: rename
    public boolean isAllowed(BotRequest botRequest);
}
