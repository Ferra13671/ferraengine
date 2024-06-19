package com.ferra.ferraengine.IO;

import com.ferra.ferraengine.IO.Utils.TextureMode;
import com.ferra.ferraengine.Logger.Logger;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * @author Ferra13671
 */

public class Texture {
    Logger logger = new Logger(Texture.class);

    private final int texId;

    private int width;
    private int height;

    /**
     * Loads an image into the texture along the specified path and in the specified mode.
     *
     * @param path   Paths to the image file.
     * @param mode   Select how the image path will be treated: inside the jar file or outside.
     */
    public Texture(String path, TextureMode mode) {
        texId = glGenTextures();


        if (mode == TextureMode.OUTSIDEJAR) {
            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);
            ByteBuffer image = stbi_load(path, width, height, channels, 0);

            if (image != null) {

                glBindTexture(GL_TEXTURE_2D, texId);


                //Texture parameters
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

                this.width = width.get(0);
                this.height = height.get(0);

                stbi_image_free(image);
            } else {
                logger.logWarn("Failed to retrieve an image along the path: " + path);
                BufferedImage img = genNotFoundImage();
                this.width = img.getWidth();
                this.height = img.getHeight();

                generateOutsideImage(img);
            }
        } else {
            try {
                BufferedImage bufferedImage = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(path));

                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();

                generateOutsideImage(bufferedImage);
            } catch (IOException | IllegalArgumentException e) {
                logger.logWarn("Failed to load the image inside the jar on the path: " + path);
                BufferedImage img = genNotFoundImage();
                this.width = img.getWidth();
                this.height = img.getHeight();

                generateOutsideImage(img);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getTexId() {
        return this.texId;
    }

    public int getWidth() {
        return width;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texId);
    }

    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }



    public void generateOutsideImage(BufferedImage bufferedImage) {
        int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());

        ByteBuffer data = ByteBuffer.allocateDirect((bufferedImage.getWidth() * bufferedImage.getHeight() * 4));

        for (int y = 0; y < bufferedImage.getHeight(); y++)
        {
            for (int x = 0; x < bufferedImage.getWidth(); x++)
            {
                int pixel = pixels[y * bufferedImage.getWidth() + x];
                data.put((byte) ((pixel >> 16) & 0xFF));
                data.put((byte) ((pixel >> 8) & 0xFF));
                data.put((byte) (pixel & 0xFF));
                data.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        data.flip();
        glBindTexture(GL_TEXTURE_2D, texId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static BufferedImage genNotFoundImage() {
        BufferedImage nf = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D graphics = (Graphics2D) nf.getGraphics();

        graphics.setBackground(Color.DARK_GRAY);
        graphics.clearRect(0, 0, 64, 64);

        graphics.setColor(Color.MAGENTA);
        graphics.fillRect(0, 0, 32, 32);
        graphics.fillRect(32, 32, 32, 32);

        BufferedImage nf2 = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);

        graphics = (Graphics2D) nf2.getGraphics();

        graphics.drawImage(nf, 0, 0, null);
        graphics.drawImage(nf, 64, 0, null);
        graphics.drawImage(nf, 64, 64, null);
        graphics.drawImage(nf, 0, 64, null);

        nf.flush();

        return nf2;
    }
}
