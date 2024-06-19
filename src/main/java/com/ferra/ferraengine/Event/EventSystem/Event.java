package com.ferra.ferraengine.Event.EventSystem;

import com.ferra.ferraengine.Event.EventSystem.EventModifiers.EventPhase;

/**
 * @author Ferra13671
 */

public abstract class Event {
    private final EventPhase eventPhase;

    public Event(EventPhase eventPhase) {
        this.eventPhase = eventPhase;
    }

    public Event() {
        this.eventPhase = EventPhase.PRE;
    }

    private boolean cancelled;

    public EventPhase getEventPhase() {
        return eventPhase;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
