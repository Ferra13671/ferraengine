package net.ferrabuild;

import com.ferra.ferraengine.IO.Texture;
import com.ferra.ferraengine.IO.Utils.TextureMode;
import com.ferra.ferraengine.Render.RenderHelper;
import com.ferra.ferraengine.Render.RenderManager;
import com.ferra.ferraengine.Utils.RainbowUtils;
import com.ferra.ferraengine.window.Sizes;
import com.ferra.ferraengine.window.Window;

import java.awt.*;
import java.nio.file.Paths;

public class AAA extends RenderManager {
    Texture texture1;
    Texture texture;

    public AAA(Window window) {
        super(window);
    }

    public void init() {
        texture1 = new Texture(Paths.get("Resources/backGround.png").toString(), TextureMode.OUTSIDEJAR);
        texture = new Texture(Paths.get("Resources/backGroundEarth.png").toString(), TextureMode.OUTSIDEJAR);
    }

    public void render() {
        super.render();

        //getWindow().setWindowPreparedSize(Sizes.HALF_THE_MONITOR_SIZE);

        System.out.println(getWindow().fpsUtil.getFPS());

        RenderHelper.drawTextureRect(0,0,this.getWindow().width,this.getWindow().height, texture1);
        RenderHelper.drawCustomTextureSizeRect(0,0,this.getWindow().width,this.getWindow().height,0,0,1,1, texture);
        //RenderHelper.drawTextureRect(-1,-1,1,1, texture);

        Color color = RainbowUtils.getCurrentRainbow(100);

        RenderHelper.drawCircle(this.getWindow().width / 2, this.getWindow().height / 2, 40,  color);
        //RenderHelper.drawGradientRainbowRect(0,0, this.getWindow().width, this.getWindow().height / 2, RainbowUtils.RainbowRectMode.SLOW, 3);


        RenderHelper.draw4ColorRect((float)this.getWindow().width / 2,(float)this.getWindow().height / 2, this.getWindow().width, this.getWindow().height, Color.yellow, Color.cyan, Color.magenta, Color.darkGray);
        //RenderHelper.draw3ColorTriangle(-0.3f,0,-0.1f,1,1,-0.5f, Color.red, Color.yellow, Color.cyan);
    }
}
