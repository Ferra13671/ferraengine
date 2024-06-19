package com.ferra.ferraengine.IO;

import com.ferra.ferraengine.Event.Events.InputEvent;
import com.ferra.ferraengine.FerraEngine;
import com.ferra.ferraengine.IO.Utils.Action;
import com.ferra.ferraengine.IO.Utils.Key;
import com.ferra.ferraengine.window.Window;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

/**
 * A class for handling key presses and releases.
 *
 * @author Ferra13671
 */

public class KeyboardInput {
    private final Window window;

    public ArrayList<Key> activeKeys = new ArrayList<>();

    public KeyboardInput(Window window) {
        this.window = window;
    }



    public void activateInput(int key, int action) {
        if (action == Action.PRESS.state) {
            if (!activeKeys.contains(key))
                activeKeys.add(new Key(key));

            keyPressed(key);

            FerraEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.PRESS));
        } else if (action == Action.RELEASE.state) {
            activeKeys.removeIf(k -> k.key == key);

            keyReleased(key);

            FerraEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.RELEASE));
        } else {
            keyJammed(key);

            FerraEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.JAMMED));
        }
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {
        if (key == GLFW.GLFW_KEY_F11) {
            window.setWindowFullScreen(!window.isWindowFullScreen());
        }
    }

    public void keyJammed(int key) {

    }
}
