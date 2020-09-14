package org.warlodya.community.commands;

import org.springframework.context.MessageSource;
import org.warlodya.community.entities.Event;
import org.warlodya.community.interfaces.Command;
import org.warlodya.community.interfaces.NameService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

//TODO refactor
public class EventsToStringCommand implements Command<String> {
    private List<Event> events;
    private boolean includeId;
    private String languageCode;
    private NameService nameService;
    private MessageSource messageSource;

    public EventsToStringCommand(
            List<Event> events,
            boolean includeId,
            String languageCode,
            NameService nameService,
            MessageSource messageSource
    ) {
        this.events = events;
        this.includeId = includeId;
        this.languageCode = languageCode;
        this.nameService = nameService;
        this.messageSource = messageSource;
    }

    @Override
    public String execute() {
        var sb = new StringBuilder();
        for (Event event : events) {
            String[] args = new String[]{
                    event.getEventName(),
                    event.getMessage(),
                    event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    nameService.getAuthorName(event.getCreator())};
            var eventMessage = messageSource.getMessage("events.list", args, new Locale(languageCode));
            if (includeId) {
                sb.append("(%d) ".formatted(event.getEventId()));
            }
            sb.append(eventMessage);
        }
        return sb.toString();
    }
}
