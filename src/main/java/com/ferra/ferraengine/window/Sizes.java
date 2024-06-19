package com.ferra.ferraengine.window;

import java.awt.*;

/**
 * @author Ferra13671
 */

public enum Sizes {
    /**
     * The resolution of your monitor divided by 2. For example 1920x1080 -> 960x540.
     */
    HALF_THE_MONITOR_SIZE(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2),

    /**
     * QVGA / SIF
     */
    F320x240(320, 240),

    /**
     * CIF
     */
    F320x288(320, 288),

    /**
     * WQVGA
     */
    F400x240(400, 240),

    /**
     * HVGA
     */
    F640x240(640, 240),

    /**
     * VGA
     */
    F320x480(320, 480),

    /**
     * WVGA
     */
    F800x480(800, 480),

    /**
     * SVGA
     */
    F800x600(800, 600),

    /**
     * VGA (16:9)
     */
    F854x480(854, 480),

    /**
     * qHD
     */
    F960x540(960, 540),

    /**
     * SVGA (16:9)
     */
    F1066x600(1066, 600),

    /**
     * XGA
     */
    F1024x768(1024, 768),

    /**
     * XGA+
     */
    F1152x864(1152, 864),

    /**
     * WXVGA
     */
    F1200x600(1200, 600),

    /**
     * HD 720p
     */
    F1280x720(1280, 720),

    /**
     * WXGA
     */
    F1280x768(1280, 768),

    /**
     * SXGA
     */
    F1280x1024(1280, 1024),
    /**
     * SXGA
     */
    F1366x768(1366, 768),

    /**
     * WXGA+
     */
    F1440x900(1440, 900),

    /**
     * SXGA+
     */
    F1400x1050(1400, 1050),

    /**
     * XJXGA
     */
    F1536x960(1536, 960),

    /**
     * WSXGA
     */
    F1536x1024(1536, 1024),

    /**
     * WXGA++
     */
    F1600x900(1600, 900),

    /**
     * WSXGA
     */
    F1600x1000(1600, 1000),

    /**
     * UXGA
     */
    F1600x1200(1600, 1200),

    /**
     * WSXGA+
     */
    F1600x1050(1600, 1050),

    /**
     * Full HD 1080p
     */
    F1920x1080(1920, 1080),

    /**
     * WUXGA
     */
    F1920x1200(1920, 1200),

    /**
     * 2K
     */
    F2048x1024(2048, 1024),

    /**
     * QWXGA
     */
    F2048x1152(2048, 1152),

    /**
     * QXGA
     */
    F2048x1536(2048, 1536),

    /**
     * Quad HD 1440p
     */
    F2560x1440(2560, 1440),

    /**
     * WQXGA
     */
    F2560x1600(2560, 1600),

    /**
     * QSXGA
     */
    F2560x2048(2560, 2048),

    /**
     * 3K
     */
    F3072x1536(3072, 1536),

    /**
     * WQXGA
     */
    F3200x1800(3200, 1800),

    /**
     * WQSXGA
     */
    F3200x2048(3200, 2048),

    /**
     * QUXGA
     */
    F3200x2400(3200, 2400),

    /**
     * QHD
     */
    F3400x1440(3400, 1440),

    /**
     * WQUXGA
     */
    F3840x2400(3840, 2400),

    /**
     * 2160p
     */
    F3840x2160(3840, 2160),

    /**
     * 4K UHD
     */
    F4096x2048(4096, 2048),

    /**
     * DQHD
     */
    F5120x1440(5120, 1440),

    /**
     * 5K UHD
     */
    F5120x2560(5120, 2560),

    /**
     * HSXGA
     */
    F5120x4096(5120, 4096),

    /**
     * 6K UHD
     */
    F6144x3072(6144, 3072),

    /**
     * WHSXGA
     */
    F6400x4096(6400, 4096),

    /**
     * HUXGA
     */
    F6400x4800(6400, 4800),

    /**
     * 7K UHD
     */
    F7168x3584(7168, 3584),

    /**
     * 8K UHD (Ultra HD) / 4320p / Super Hi-Vision
     */
    F7680x4320(7680, 4320),

    /**
     * WHUXGA
     */
    F7680x4800(7680, 4800),

    /**
     * 8K UHD
     */
    F8192x4096(8192, 4096);

    private final int width;
    private final int height;



    Sizes(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
