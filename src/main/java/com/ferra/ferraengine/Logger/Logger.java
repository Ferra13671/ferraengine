package com.ferra.ferraengine.Logger;

import java.util.Date;

/**
 * A special utility to simplify the output of various information to the console.
 * In addition to text, it outputs the current time the message was sent, the message type, and the name of the logger that sent the message.
 *
 * @author Ferra13671
 */

public class Logger {
    public final String name;

    /**
     * @param name  Logger name
     */
    public Logger(String name) {
        this.name = name;
    }

    /**
     * @param c   The class whose name will be used as the name of the logger.
     */
    public Logger(Class c) {
        this.name = c.getSimpleName();
    }

    /**
     * Sends a message labeled DEBUG. Use it to send various debug information.
     *
     * @param message    The message that will be sent.
     */
    public void logDebug(String message) {
        logMessage(message, MessageCategory.DEBUG);
    }

    /**
     * Sends a message labeled INFO. Use it to send normal information.
     *
     * @param message    The message that will be sent.
     */
    public void logInfo(String message) {
        logMessage(message, MessageCategory.INFO);
    }

    /**
     * Sends a message labeled WARN. Use it to send warnings.
     *
     * @param message    The message that will be sent.
     */
    public void logWarn(String message) {
        logMessage(message, MessageCategory.WARN);
    }

    /**
     * Sends a message labeled ERROR. Use it to send errors that may cause the application to crash.
     *
     * @param message    The message that will be sent.
     */
    public void logError(String message) {
        logMessage(message, MessageCategory.ERROR);
    }

    /**
     * Sends a message labeled FATAL. Use it to send fatal errors that will 100% cause the application to crash.
     *
     * @param message    The message that will be sent.
     */
    public void logFatal(String message) {
        logMessage(message, MessageCategory.FATAL);
    }


    private void logMessage(String message, MessageCategory category) {

        System.out.println(getCurrentDate() + " " + getMessageCategory(category) + " " + name() + ": " + message);
    }

    /**
     * @return The current time according to the scheme: [Hours:Minutes:Seconds].
     */
    public String getCurrentDate() {
        Date currentDate = new Date();

        return "[" + currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds() + "]";
    }

    /**
     * @return The current name of the logger in square brackets.
     */
    public String name() {
        return "[" + this.name + "]";
    }

    private String getMessageCategory(MessageCategory category) {
        return "[" + category.name() + "]";
    }


    public enum MessageCategory {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FATAL
    }
}
