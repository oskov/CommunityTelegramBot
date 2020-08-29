package org.warlodya.community;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SavedUpdates {

    public static Update messageUpdate(@Value("classpath:path/to/file") Resource resourceFile) {
        return new Update();
    }
}
