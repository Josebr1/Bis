package br.com.casadocodigo.bis.screens;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.control.Button;
import br.com.casadocodigo.bis.game.interfaces.ButtonDelegate;
import br.com.casadocodigo.bis.game.interfaces.PauseDelegate;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by antonio on 10/30/17.
 */

public class PauseScreen extends CCLayer implements ButtonDelegate {
    private Button resumeButton;
    private Button quitButton;
    private CCColorLayer background;
    private PauseDelegate delegate;

    public void setDelegate(PauseDelegate delegate) {
        this.delegate = delegate;
    }

    public PauseScreen() {
        // habilita o toque na tela
        this.setIsTouchEnabled(true);

        // background
        this.background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
                screenWidth(),
                screenHeight());
        this.addChild(this.background);

        // logo
        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() / 2, screenHeight() - 130)));
        this.addChild(title);

        // Adiciona botoes
        // botoes
        this.resumeButton = new Button(Assets.PLAY);
        this.quitButton = new Button(Assets.EXIT);
        this.resumeButton.setDelegate(this);
        this.quitButton.setDelegate(this);
        this.addChild(this.resumeButton);
        this.addChild(this.quitButton);

        // Posiciona botoes
        this.resumeButton.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() / 2, screenHeight() - 250)));
        this.quitButton.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() / 2, screenHeight() - 300)));
    }

    @Override
    public void buttonClicked(Button sender) {
        // Verifica se o botão foi pressionado
        if (sender == this.resumeButton) {
            this.delegate.resumeGame();
            this.removeFromParentAndCleanup(true);
        }
        // Verifica se o botao foi pressionado
        if (sender == this.quitButton) {
            this.delegate.quitGame();
        }
    }
}