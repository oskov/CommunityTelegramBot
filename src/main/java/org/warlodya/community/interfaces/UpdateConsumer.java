package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateConsumer {
    public void consumeUpdate(Update update);
}
