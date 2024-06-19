package net.ferrabuild;

import com.ferra.ferraengine.Event.EventSystem.EventSubscriber;
import com.ferra.ferraengine.Event.Events.InputEvent;
import com.ferra.ferraengine.IO.Utils.Action;
import com.ferra.ferraengine.Logger.Logger;
import com.ferra.ferraengine.window.Sizes;
import com.ferra.ferraengine.window.Window;


import java.nio.file.Paths;

public class Start {
    public static Window window;

    public static void main(String[] args) {
        window = new Window();
        window.createWindow(800, 600);
        window.setWindowTitle("Ferra Engine");
        //window.setWindowFullScreen(true);
        window.setWindowIcon(Paths.get("Resources/Icon16.png").toString());
        window.setWindowPreparedSize(Sizes.HALF_THE_MONITOR_SIZE);

        Logger logger = new Logger(Start.class);

        logger.logDebug("Test debug!");
        logger.logInfo("Test info!");
        logger.logWarn("Test warn!");
        logger.logError("Test error!");
        logger.logFatal("Test fatal!");

        window.renderManager = new AAA(window);

        window.loop();
        window.destroyWindow();
    }


    @EventSubscriber
    public void a(InputEvent.KeyInputEvent e) {
        if (e.getAction() == Action.PRESS)
            System.out.println("Test pressed!");
        else if (e.getAction() == Action.RELEASE)
            System.out.println("Test released!");
        else
            System.out.println("Test jammed!");
    }
}
