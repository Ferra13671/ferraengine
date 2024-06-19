package com.ferra.ferraengine.IO;

import com.ferra.ferraengine.IO.Utils.Action;
import com.ferra.ferraengine.IO.Utils.Key;

import java.util.ArrayList;

/**
 * A class for handling mouse button presses and releases.
 *
 * @author Ferra13671
 */

public class MouseInput {

    ArrayList<Key> buttons = new ArrayList<>();



    public void activateInput(int button, Action action) {
        if (action == Action.PRESS) {
            boolean needAdd = true;

            for (Key key: buttons) {
                if (key.key == button) {
                    needAdd = false;
                    break;
                }
            }

            if (needAdd)
                buttons.add(new Key(button));

            buttonPressed(button);
        } else {
            buttons.removeIf(k -> k.key == button);

            buttonReleased(button);
        }
        System.out.println(action.state);
    }


    public void buttonPressed(int button) {

    }

    public void buttonReleased(int button) {

    }
}
