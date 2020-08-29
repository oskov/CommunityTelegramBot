package org.warlodya.community.repositories;

import org.springframework.data.repository.CrudRepository;
import org.warlodya.community.entities.Event;

public interface EventCrudRepository extends CrudRepository<Event, Integer> {
}
