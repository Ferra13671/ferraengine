package com.ferra.ferraengine;

import com.ferra.ferraengine.Event.EventSystem.EventSystem;

/**
 * A class containing general data, basic information about the current version of Ferra2DEngine as well as event handler, etc.
 *
 * @author Ferra13671
 */

public class FerraEngine {

    private static final String version = "1.0";
    private static final String nameEngine = "Ferra 2D Engine";

    public static final EventSystem SYS_EVENT_BUS = new EventSystem("SEB", false);

    public static String getEngineFullName() {
        return nameEngine + version;
    }

    public static String getEngineVersion() {
        return version;
    }

    public static String getEngineName() {
        return nameEngine;
    }
}
