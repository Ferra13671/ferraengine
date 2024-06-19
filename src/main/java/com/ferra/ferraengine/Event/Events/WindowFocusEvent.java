package com.ferra.ferraengine.Event.Events;

import com.ferra.ferraengine.Event.EventSystem.Event;

/**
 * @author Ferra13671
 */

public class WindowFocusEvent extends Event {
    private final boolean focused;

    public WindowFocusEvent(boolean focused) {
        this.focused = focused;
    }

    public boolean getFocused() {
        return this.focused;
    }
}
