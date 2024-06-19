package com.ferra.ferraengine.Event.EventSystem;

import com.ferra.ferraengine.Logger.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility for adding an event system to your project.
 * <p>
 * To add methods to the list of registered methods, you need to mark them with the {@link EventSubscriber} annotation,
 * put the desired event as input, and use {@code register(Object object)} to register the class they reside in. Example method:
 * <p>
 * {@code
 * @EventSubscriber }
 * <p>
 * {@code public void onInput(InputEvent.KeyInputEvent e) }
 * <p>
 * {@code {
 *     System.out.println("KeyInputEvent activated!");
 * }
 * }.
 * <p>
 * <p>
 * If one of the events has been activated in the handler,
 * all registered methods using that event as input will be activated.
 * <p>
 * In addition to the standard events provided by FerraEngine, you can create and activate your own events.
 * To do this, you need to create a class that extends the {@link Event} class.
 * To activate it in the desired handler, use the {@code activete(your event)} method.
 * <p>
 * Any actions with the event system must be performed strictly after the window has been created ({@code Window.createWindow(int width , int height)}).
 * <b>Failure to follow this rule may result in a crash during execution.</b>
 *
 * @author Ferra13671
 */

public class EventSystem {

    protected Logger logger;
    public boolean debugLogging;


    public EventSystem(final String name, boolean debugLogging) {
        this.logger = new Logger(name);
        this.debugLogging = debugLogging;
    }




    private final HashMap<Object, List<Method>> registeredMethods = new HashMap<>();

    public void register(Object object) {

        registeredMethods.put(object, Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(EventSubscriber.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(EventSubscriber.class).priority()))
                .collect(Collectors.toList())
        );

        /*
        if (debugLogging) {

        }
         */
    }

    public void unregister(Object object) {

        registeredMethods.remove(object);

        /*
        if (debugLogging) {

        }
        */
    }

    public <T extends Event> void activate(T event) {
        if (event.isCancelled()) return;

        HashMap<Object, List<Method>> registeredMap = this.registeredMethods;

        registeredMap.forEach((object, methods) -> methods
                .forEach(method -> {
                    if (method.getParameterTypes()[0] != event.getClass()) return;

                    try {
                        method.invoke(object, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        /*
                        if (debugLogging) {

                        }
                         */

                        e.printStackTrace();
                    }
                })
        );

        /*
        if (debugLogging) {

        }
        */
    }

    public <T> T activate(T event) {

        HashMap<Object, List<Method>> registeredMap = this.registeredMethods;

        registeredMap.forEach((object, methods) -> methods
                .forEach(method -> {
                    if (method.getParameterTypes()[0] != event.getClass()) return;

                    try {
                        method.invoke(object, event);
                    } catch (IllegalAccessException | InvocationTargetException exception) {
                        /*
                        if (debugLogging) {

                        }
                         */

                        exception.printStackTrace();
                    }
                })
        );
        return event;
    }
}
