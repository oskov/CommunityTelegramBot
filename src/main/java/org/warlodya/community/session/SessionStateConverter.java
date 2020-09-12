package org.warlodya.community.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class SessionStateConverter implements AttributeConverter<SessionState, String> {
    @Override
    public String convertToDatabaseColumn(SessionState sessionState) {

        String sessionString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            sessionString = objectMapper.writeValueAsString(sessionState);
        } catch (final JsonProcessingException e) {
            // TODO: logs
        }

        return sessionString;
    }

    @Override
    public SessionState convertToEntityAttribute(String dbData) {

        SessionState sessionState = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            sessionState = objectMapper.readValue(dbData, SessionState.class);
        } catch (final IOException e) {
            // TODO: logs
        }

        return sessionState;
    }
}
