package org.warlodya.community.repositories;

import org.springframework.data.repository.CrudRepository;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.entities.Event;

import java.util.List;

public interface EventCrudRepository extends CrudRepository<Event, Integer> {
    public List<Event> findTop10ByOrderByEventDateAsc();

    public List<Event> findByCreator(BotUser creator);
}
