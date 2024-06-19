package com.ferra.ferraengine.Utils;

import com.ferra.ferraengine.Logger.Logger;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

/**
 * @author Ferra13671
 */

public class IconUtil {

    private static final Logger logger = new Logger(IconUtil.class);


    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private final ByteBuffer image;
    private final int width, height;

    IconUtil(int width, int height, ByteBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
    }
    public static IconUtil loadImage(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                logger.logWarn("Couldn't get the picture to load!");
            }
            width = w.get();
            height = h.get();
        }
        return new IconUtil(width, height, image);
    }
}
