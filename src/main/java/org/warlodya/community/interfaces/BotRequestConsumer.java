package org.warlodya.community.interfaces;

import org.warlodya.community.request.BotRequest;

public interface BotRequestConsumer {
    void consumeBotRequest(BotRequest botRequest);
}
