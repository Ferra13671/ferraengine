package com.ferra.ferraengine.window;

import com.ferra.ferraengine.Event.Events.WindowFocusEvent;
import com.ferra.ferraengine.FerraEngine;
import com.ferra.ferraengine.IO.Utils.Action;
import com.ferra.ferraengine.IO.KeyboardInput;
import com.ferra.ferraengine.IO.MouseInput;
import com.ferra.ferraengine.Render.RenderManager;
import com.ferra.ferraengine.Utils.FPSUtil;
import com.ferra.ferraengine.Utils.IconUtil;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * A class designed to simplify the creation and interaction with a window.
 * There are many different functions that do not need to be created manually.
 * <p>
 * Creating more than 1 window is not supported in the code(although you can freely create multiple windows).
 * Creating multiple windows is guaranteed to cause problems.
 *
 * @author Ferra13671
 */

public class Window {

    public long window;

    public KeyboardInput keyboardInput = new KeyboardInput(this);
    public MouseInput mouseInput = new MouseInput();
    public RenderManager renderManager = new RenderManager(this);

    public int width;
    public int height;
    private int oldWidth;
    private int oldHeight;

    public int xPos;
    public int yPos;
    private int oldXPos;
    private int oldYPos;

    public String title = "";

    public GLFWImage.Buffer currentIcon;

    public boolean isFullScreen;

    public FPSUtil fpsUtil;

    public boolean isLooped = true;


    public Window() {}


    /**
     * Creates a basic window ready for use.
     *
     * @param windowWidth    Length of the new window in pixels.
     * @param windowHeight    Height of the new window in pixels.
     */
    public void createWindow(int windowWidth , int windowHeight) {
        long startTime = System.currentTimeMillis();

        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        //Preparing GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        //glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_AUTO_ICONIFY, GLFW_TRUE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
        //glfwWindowHint ( GLFW_TRANSPARENT_FRAMEBUFFER , GLFW_TRUE );


        //Creating a new window
        window = glfwCreateWindow( windowWidth, windowHeight, title, NULL, NULL);
        isFullScreen = false;
        this.width = windowWidth;
        this.height = windowHeight;
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            keyboardInput.activateInput(key, action);
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mouseInput.activateInput(button, action == GLFW_PRESS ? Action.PRESS : Action.RELEASE);
            }
        });

        glfwSetWindowFocusCallback(window, new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                FerraEngine.SYS_EVENT_BUS.activate(new WindowFocusEvent(focused));
            }
        });

        glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width_, int height_) {
                if (width_ != 0 && height_ != 0) {
                    glLoadIdentity();
                    glTranslatef(-1, -1, 0);
                    glScalef(2 / (float) width_, 2 / (float) height_, 1);
                    glViewport(0, 0, width_, height_);
                    width = width_;
                    height = height_;
                    System.out.println(width_ + "   " + height_);
                }
            }
        });

        glfwSetWindowPosCallback(window, new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                xPos = xpos;
                yPos = ypos;
            }
        });


        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        fpsUtil = new FPSUtil();

        long time = System.currentTimeMillis() - startTime;
        System.out.println(time);

        GL.createCapabilities();

        glTranslatef(-1,-1,0);
        glScalef(2 / (float)this.width, 2 / (float)this.height, 1);
    }

    public void loop() {
        renderManager.init();

        while ( !glfwWindowShouldClose(window) ) {

            fpsUtil.updateFPS();

            if (!isLooped)
                break;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            renderManager.render();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }


    /**
     * Closes your window.
     */
    public void destroyWindow() {

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    /**
     * Sets the entered text as the window name.
     * Only English language is supported.
     */
    public void setWindowTitle(Object title) {
        glfwSetWindowTitle(window, title.toString());
        this.title = title.toString();
    }


    /**
     * Enables or disables the window's full-screen mode.
     *
     * @param in    Value specifying whether full screen mode will be enabled(true) or disabled(false).
     */
    public void setWindowFullScreen(boolean in) {
        if (in) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            oldWidth = width;
            oldHeight = height;

            oldXPos = xPos;
            oldYPos = yPos;

            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, screenSize.width, screenSize.height, 120);

            isFullScreen = true;
        } else {

            glfwSetWindowMonitor(window, NULL, 0, 0, oldWidth, oldHeight, 120);

            setWindowPos(oldXPos, oldYPos);

            isFullScreen = false;
        }
    }


    /**
     * Sets the image as the icon for this window.
     *
     * @param path  The name or path to the desired picture.
     */
    public void setWindowIcon(String path) {
        final IconUtil icon = IconUtil.loadImage(path);
        GLFWImage image = GLFWImage.malloc();
        currentIcon = null;
        currentIcon = GLFWImage.malloc(1);
        image.set(icon.getWidth(), icon.getHeight(), icon.getImage());
        currentIcon.put(0, image);
        glfwSetWindowIcon(window, currentIcon);
    }


    /**
     * Sets the upper left corner of the window (excluding the frame) to the specified coordinates.
     *
     * @param x length position (in pixels)
     * @param y height position (in pixels)
     */
    public void setWindowPos(int x, int y) {
        glfwSetWindowPos(window, x, y);
    }


    /**
     * @return Whether full screen mode is enabled or not
     */
    public boolean isWindowFullScreen() {
        return isFullScreen;
    }


    /**
     * Sets the window size according to the selected ready-made configuration.
     *
     * @param size   A pre-made version of the window size that conforms to monitor resolution standards.
     */
    public void setWindowPreparedSize(Sizes size) {
        glfwSetWindowSize(window, size.getWidth(), size.getHeight());

    }


    /**
     * Sets the window size to the specified width and height.
     *
     * @param width   Desired window width (in pixels).
     * @param height   Desired window height (in pixels).
     */
    public void setWindowSize(int width, int height) {
        if (width < 0 || height < 0) {
            return;
        }
        glfwSetWindowSize(window, width, height);
    }


    /**
     * Draw attention by highlighting this window (if supported by the system).
     */
    public void attentionRequest() {
        glfwRequestWindowAttention(window);
    }


    /**
     * Changes the transparency of the window to the selected one.
     *
     * @param alpha   Real number from 0 to 1.
     */
    public void setWindowOpacity(float alpha) {
        glfwSetWindowOpacity(window, alpha);
    }
}
