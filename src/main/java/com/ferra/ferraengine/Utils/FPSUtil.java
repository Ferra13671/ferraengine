package com.ferra.ferraengine.Utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Utility to process and retrieve the current window fps.
 *
 * @author Ferra13671
 */

public class FPSUtil {
    private double prevTime;
    private int frameCount;
    private int currentFPS;

    public FPSUtil () {
        prevTime = glfwGetTime();
        frameCount = 0;
        currentFPS = 0;
    }


    /**
     * Updates the fps counter loop.
     * <b> Used only in the window render loop. </b>
     */
    public void updateFPS() {
        double currentTime = glfwGetTime();
        frameCount++;
        if ( currentTime - prevTime >= 1.0 )
        {
            currentFPS = frameCount;

            frameCount = 0;
            prevTime = currentTime;
        }
    }

    /**
     * @return   Current fps.
     */
    public int getFPS() {
        return this.currentFPS;
    }
}
