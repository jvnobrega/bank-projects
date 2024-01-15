package com.techbank.account.cmd.infrastructure;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvent(
                aggregate.getId(),
                aggregate.getUncommittedChanges(),
                aggregate.getVersion());

        aggregate.markChangesAsCommitted();

    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (nonNull(events) && !events.isEmpty()) {
            aggregate.replayEvents(events);
            events.stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder())
                    .ifPresent(aggregate::setVersion);
        }
        return aggregate;
    }
}
