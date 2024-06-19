package com.ferra.ferraengine.Render;

import com.ferra.ferraengine.IO.Texture;
import com.ferra.ferraengine.Utils.RainbowUtils;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;

/**
 * A class that contains methods for rendering various shapes or textures more easily.
 *
 * @author Ferra13671
 */

public class RenderHelper {
    private static final FloatBuffer textureCords = ByteBuffer.allocateDirect(8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer().put(new float[]{0,1  ,1,1,  1,0 ,0,0}).position(0);
    private static final FloatBuffer customTextureCords = ByteBuffer.allocateDirect(8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private static final FloatBuffer rectVertex = ByteBuffer.allocateDirect(8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();


    /**
     * Enables blend(for normal rendering of transparency).

     * <b> In the {@code drawTextureRect(float x1, float y1, float x2, ...)}
     * and {@code drawCustomTextureSizeRect(float x1, float y1, float x2, ...)}
     * methods, this is done automatically. </b>
     */
    public static void enableBlend() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }


    /**
     * Disables blend.
     */
    public static void disableBlend() {
        glDisable(GL_BLEND);
    }

    /**
     * Draws a rectangle at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color   Rectangle color.
     */
    public static void drawRect(float x1, float y1, float x2, float y2, Color color) {
        draw4ColorRect(x1,y1,x2,y2,color,color,color,color);
    }

    /**
     * Draws a rectangle with a vertical gradient at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color1   y1 color.
     * @param color2   y2 color.
     */
    public static void drawVerticalGradientRect(float x1, float y1, float x2, float y2, Color color1, Color color2) {
        draw4ColorRect(x1,y1,x2,y2, color1, color2, color1, color2);
    }

    /**
     * Draws a rectangle with a horizontal gradient at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color1   x1 color.
     * @param color2   x2 color.
     */
    public static void drawHorizontalGradientRect(float x1, float y1, float x2, float y2, Color color1, Color color2) {
        draw4ColorRect(x1,y1,x2,y2, color1, color1, color2, color2);
    }

    /**
     * Draws a four-color rectangle at the given coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param x1y1Color   x1 y1 color.
     * @param x1y2Color   x1 y2 color.
     * @param x2y1Color   x2 y1 color.
     * @param x2y2Color   x2 y2 color.
     */
    public static void draw4ColorRect(float x1, float y1, float x2, float y2, Color x1y1Color, Color x1y2Color, Color x2y1Color, Color x2y2Color) {
        glPushMatrix();

        glBegin(GL_QUADS);
        glColor4f(x1y1Color.getRed() / 255f, x1y1Color.getGreen() / 255f, x1y1Color.getBlue() / 255f, x1y1Color.getAlpha() / 255f); glVertex2f(x1,y1);
        glColor4f(x1y2Color.getRed() / 255f, x1y2Color.getGreen() / 255f, x1y2Color.getBlue() / 255f, x1y2Color.getAlpha() / 255f); glVertex2f(x1,y2);
        glColor4f(x2y2Color.getRed() / 255f, x2y2Color.getGreen() / 255f, x2y2Color.getBlue() / 255f, x2y2Color.getAlpha() / 255f); glVertex2f(x2,y2);
        glColor4f(x2y1Color.getRed() / 255f, x2y1Color.getGreen() / 255f, x2y1Color.getBlue() / 255f, x2y1Color.getAlpha() / 255f); glVertex2f(x2,y1);
        glEnd();

        glPopMatrix();
    }


    /**
     * Draws a rectangle with a texture at the given coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texture   The texture that will be applied to the rectangle.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, Texture texture) {
        rectVertex.put(new float[]{x1,y1, x2,y1, x2,y2, x1,y2}).position(0);

        glColor3f(1,1,1);
        glPushMatrix();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glVertexPointer(2, GL_FLOAT, 0, rectVertex);
        glTexCoordPointer(2, GL_FLOAT, 0, textureCords);

        texture.bind();
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glPopMatrix();
    }


    /**
     * Draws a rectangle with a custom texture position.
     * Suitable for those who use 1 texture as a texture atlas.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texPosX1   Initial x coordinate inside the texture.
     * @param texPosY1   Initial y coordinate inside the texture.
     * @param texPosX2   End x coordinate inside the texture.
     * @param texPosY2   End y coordinate inside the texture.
     * @param texture   The texture that will be applied to the rectangle.
     */
    public static void drawCustomTextureSizeRect(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, Texture texture) {
        rectVertex.put(new float[]{x1,y1, x2,y1, x2,y2, x1,y2}).position(0);
        customTextureCords.put(new float[]{texPosX1,texPosY2   ,texPosX2,texPosY2   ,texPosX2,texPosY1   ,texPosX1,texPosY1}).position(0);

        glColor3f(1,1,1);
        glPushMatrix();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glVertexPointer(2, GL_FLOAT, 0, rectVertex);
        glTexCoordPointer(2, GL_FLOAT, 0, customTextureCords);

        texture.bind();
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glPopMatrix();
    }


    /**
     * Draws a triangle at the specified coordinates.
     *
     * @param x1   x coordinate of the first point.
     * @param y1   y coordinate of the first point
     * @param x2   x coordinate of the second point.
     * @param y2   y coordinate of the second point.
     * @param x3   x coordinate of the third point.
     * @param y3   y coordinate of the third point.
     * @param color   Triangle color.
     */
    public static void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color) {
        draw3ColorTriangle(x1,y1,x2,y2,x3,y3,color,color,color);
    }

    /**
     * Draws a three-color triangle at the specified coordinates.
     *
     * @param x1   x coordinate of the first point.
     * @param y1   y coordinate of the first point
     * @param x2   x coordinate of the second point.
     * @param y2   y coordinate of the second point.
     * @param x3   x coordinate of the third point.
     * @param y3   y coordinate of the third point.
     * @param color1   The color of the first point.
     * @param color2   The color of the second point.
     * @param color3   The color of the third point.
     */
    public static void draw3ColorTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color1, Color color2, Color color3) {
        glPushMatrix();

        glBegin(GL_TRIANGLES);
        glColor4f(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color1.getAlpha() / 255f); glVertex2f(x1,y1);
        glColor4f(color2.getRed() / 255f, color2.getGreen() / 255f, color2.getBlue() / 255f, color2.getAlpha() / 255f); glVertex2f(x2,y2);
        glColor4f(color3.getRed() / 255f, color3.getGreen() / 255f, color3.getBlue() / 255f, color3.getAlpha() / 255f); glVertex2f(x3,y3);
        glEnd();

        glPopMatrix();
    }


    /**
     * Draws a circle at the specified coordinates and size.
     *
     * @param x   Circle center by x coordinates.
     * @param y   Circle center by y coordinates.
     * @param size   Circle size.
     * @param color   Circle color.
     */
    public static void drawCircle(float x, float y, float size, Color color) {
        glPushMatrix();
        glTranslatef(x, y, 0);

        double x1,y1;
        byte cnt = 40;
        float a = (float)Math.PI * 2 / cnt;

        glBegin(GL_TRIANGLE_FAN);
        glColor3f(color.getRed() / 255f, color.getGreen() / 255f,color.getBlue() / 255f);
        glVertex2d(0,0);
        for (int i = -1; i < cnt; i++) {
            x1 = Math.sin(a * i) * size;
            y1 = Math.cos(a * i) * size;
            glVertex2d(x1,y1);
        }
        glEnd();
        glPopMatrix();
    }

    /**
     * Draws an oval at the specified coordinates and dimensions.
     *
     * @param x   Center of the oval at x coordinates.
     * @param y   Center of the oval at y coordinates.
     * @param widthSize   Size by x coordinates.
     * @param heightSize   Size by y coordinates.
     * @param color   The color of this oval.
     */
    public static void drawOval(float x, float y, float widthSize, float heightSize, Color color) {
        glPushMatrix();
        glTranslatef(x, y, 0);

        double x1,y1;
        byte cnt = 40;
        float a = (float)Math.PI * 2 / cnt;

        glBegin(GL_TRIANGLE_FAN);
        glColor3f(color.getRed() / 255f, color.getGreen() / 255f,color.getBlue() / 255f);
        glVertex2d(0,0);
        for (int i = -1; i < cnt; i++) {
            x1 = Math.sin(a * i) * widthSize;
            y1 = Math.cos(a * i) * heightSize;
            glVertex2d(x1,y1);
        }
        glEnd();
        glPopMatrix();
    }


    public static void drawFigureWithCustomNumberOfAngles(float x, float y, float size,int angles, Color color) {
        if (angles == 4) {
            drawRect(x - size, y - size, x + size, y + size, color);
        } else if (angles < 3) return;

        glPushMatrix();
        glTranslatef(x, y, 0);

        double x1,y1;
        float a = (float)Math.PI * 2 / angles;

        glBegin(GL_TRIANGLE_FAN);
        glColor3f(color.getRed() / 255f, color.getGreen() / 255f,color.getBlue() / 255f);
        glVertex2d(0,0);
        for (int i = -1; i < angles; i++) {
            x1 = Math.sin(a * i) * size;
            y1 = Math.cos(a * i) * size;
            glVertex2d(x1,y1);
        }
        glEnd();
        glPopMatrix();
    }


    /**
     * Draws a rainbow rectangle according to specified coordinates, rainbow speed mode and standard quality.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param rainbowType   Rainbow Speed Mode.
     *
     * @implNote <b> FAST and VERYFAST modes are not recommended because color breaks are noticeable when using them. </b>
     */
    public static void drawGradientRainbowRect(int x1, int y1, int x2, int y2, RainbowUtils.RainbowRectMode rainbowType) {
        drawGradientRainbowRect( x1, y1, x2, y2, rainbowType, 1);
    }


    /**
     * Draws a rainbow rectangle according to the specified coordinates, rainbow speed mode, and specified quality.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param rainbowType   Rainbow Speed Mode.
     * @param qualityFactor   Factor that determines the quality of the rainbow(default 1).
     *
     * @implNote <b> FAST and VERYFAST modes are not recommended because color breaks are noticeable when using them. </b>
     * <p></p>
     * For small rectangles standard quality is sufficient, but for large rectangles it is better to use quality 3.
     * However, quality strongly affects performance, so it is not recommended to set it higher than 4-5.
     */
    public static void drawGradientRainbowRect(int x1, int y1, int x2, int y2, RainbowUtils.RainbowRectMode rainbowType, float qualityFactor) {
        final float[] counter = {1};
        int dX;
        int tX = x1;
        short delay = rainbowType.delay;
        float speed = rainbowType.speed;
        short factor = (short) (50 * qualityFactor);
        float counterFactor = 1 / qualityFactor;

        float fX;

        if (x1 < x2) {
            fX = x2 - x1;
            fX /= factor;
        } else {
            fX = x1 - x2;
            fX = -(fX / factor);
        }
        dX = fX != 0 ? (int) Math.ceil(fX) : 0;

        glPushMatrix();

        while (tX != x2) {

            if (x1 < x2) {
                if (tX + dX > x2) {
                    dX = x2 - tX;
                }
            } else {
                if (tX + dX < x2) {
                    dX = tX - x2;
                }
            }

            Color color = RainbowUtils.getCurrentRainbow((int)(counter[0] * delay), speed);

            glBegin(GL_QUADS);
            glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
            glVertex2f(tX,y1);
            glVertex2f(tX,y2);
            glVertex2f(tX + dX,y2);
            glVertex2f(tX + dX,y1);
            glEnd();

            tX += dX;
            counter[0] += counterFactor;
        }

        glPopMatrix();
    }
}
