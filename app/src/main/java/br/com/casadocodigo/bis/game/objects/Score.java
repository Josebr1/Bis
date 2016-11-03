package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 03/11/2016.
 */

public class Score extends CCLayer {
    private int score;
    private CCBitmapFontAtlas text;

    public Score(){
        this.score = 0;

        this.text = CCBitmapFontAtlas.bitmapFontAtlas(
                String.valueOf(this.score),
                "UniSansSemiBold_Numbers_240.fnt"
        );
        this.text.setScale((float) 240 / 240);

        this.setPosition(screenWidth() - 50, screenHeight() - 50);
        this.addChild(this.text);
    }

    /**
     * Incrementa a variável score e configura novamente
     * o texto do placar.
     */
    public void increase(){
        score++;
        this.text.setString(String.valueOf(this.score));
    }
}
