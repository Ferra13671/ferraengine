package com.ferra.ferraengine.Event.Events;

import com.ferra.ferraengine.Event.EventSystem.Event;
import com.ferra.ferraengine.IO.Utils.Action;

/**
 * @author Ferra13671
 */

public class InputEvent extends Event {
    protected final Action action;

    public InputEvent(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public static class KeyInputEvent extends InputEvent {
        private final int key;

        public KeyInputEvent(int key, Action action) {
            super(action);
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }
    }

    public static class MouseInputEvent extends InputEvent {
        private final int button;

        public MouseInputEvent(int button, Action action) {
            super(action);
            this.button = button;
        }

        public int getButton() {
            return this.button;
        }
    }
}
