package com.ferra.ferraengine.Utils;

import java.awt.*;

/**
 * A utility to make it easier to get rainbow color.
 *
 * @author Ferra13671
 */

public class RainbowUtils {
    public enum RainbowRectMode {
        SLOW(1, (byte) 40),
        NORMAL(0.5f, (byte) 20),
        FAST(0.2f, (byte) 10),
        VERYFAST(0.1f, (byte) 5);


        public final float speed;
        public final byte delay;

        RainbowRectMode(float speed, byte delay) {
            this.speed = speed;
            this.delay = delay;
        }
    }
    private static final float[] rainbowSpeeds = {1, 1, 1, 1, 0.1f, 0.1f, 0.1f, 0.1f};
    private static final short[] rainbowDelays = {100, 200, 300, 500, 750, 700, 600, 850};


    public static Color getCurrentRainbow(int  delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f);
    }

    public static Color getCurrentRainbow(int delay, float speed) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f);
    }

    public static Color getCurrentRainbowWithType(byte type) {
        float speed = RainbowUtils.rainbowSpeeds[type - 1];
        short delay = RainbowUtils.rainbowDelays[type - 1];

        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f);
    }

    public static Color getCurrentRainbowWithType(byte type, float counter) {
        float speed = RainbowUtils.rainbowSpeeds[type - 1];
        int delay = RainbowUtils.rainbowDelays[type - 1];

        delay = (int)(delay * counter);

        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f);
    }
}
