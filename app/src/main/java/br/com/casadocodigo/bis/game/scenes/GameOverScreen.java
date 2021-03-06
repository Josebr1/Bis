package br.com.casadocodigo.bis.game.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.control.Button;
import br.com.casadocodigo.bis.game.interfaces.ButtonDelegate;
import br.com.casadocodigo.bis.screens.ScreenBackground;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by antonio on 10/30/17.
 */

public class GameOverScreen extends CCLayer implements ButtonDelegate {
    private ScreenBackground background;
    private Button beginButton;

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }

    public GameOverScreen() {
        // background
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2.0f,
                        screenHeight() / 2.0f)));
        this.addChild(this.background);
        // image
        CCSprite title = CCSprite.sprite(Assets.GAMEOVER);
        title.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2,
                        screenHeight() - 130)));
        this.addChild(title);
        // habilita o toque na tela
        this.setIsTouchEnabled(true);
        this.beginButton = new Button(Assets.PLAY);
        this.beginButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2,
                        screenHeight() - 300)));
        this.beginButton.setDelegate(this);
        addChild(this.beginButton);
    }


    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.beginButton)) {
            SoundEngine.sharedEngine().pauseSound();
            CCDirector.sharedDirector()
                    .replaceScene(new TitleScreen().scene());
        }
    }

}