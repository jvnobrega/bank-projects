package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;

import java.util.Iterator;
import java.util.List;

public interface EventStore {
    void saveEvent(String aggregateId, Iterator<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
}