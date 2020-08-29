package org.warlodya.community.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateConsumer {
    boolean consumeUpdate(Update update);
}
