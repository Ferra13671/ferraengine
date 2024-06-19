package com.ferra.ferraengine.IO.Utils;

/**
 * @author Ferra13671
 */

public enum Action {

    /**
     * A state indicating that the button has been pressed.
     */
    PRESS(1),

    /**
     * A state indicating that the button has been released.
     */
    RELEASE(0),

    /**
     * A state indicating that the button is jammed. Compared to the others, this state only applies to keyboard keys.
     * <p>
     * <b> If another button is pressed while the button is jammed, the state will no longer be active. </b>
     */
    JAMMED(2);



    public final int state;

    Action(int state) {
        this.state = state;
    }
}
