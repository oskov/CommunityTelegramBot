package org.warlodya.community.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.warlodya.community.repositories.EventCrudRepository;

@Component
public class EventScheduling {
    private EventCrudRepository eventCrudRepository;

    @Autowired
    public EventScheduling(EventCrudRepository eventCrudRepository) {
        this.eventCrudRepository = eventCrudRepository;
    }

    //TODO: event processing

}
