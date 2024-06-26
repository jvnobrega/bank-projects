package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.events.BaseEvent;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    @Getter
    protected String id;
    @Getter
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private static final Logger log = Logger.getLogger(AggregateRoot.class.getName());

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, Boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}",
                    event.getClass().getName()));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(List<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
