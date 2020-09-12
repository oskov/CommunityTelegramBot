package org.warlodya.community.session;

import java.util.HashMap;
import java.util.Map;

public class SessionState {
    public Map<String, String> data;
    public Map<String, Boolean> flags;

    public SessionState() {
        data = new HashMap<>();
        flags = new HashMap<>();
    }
}
