package br.com.casadocodigo.bis.game.control;

import android.util.Log;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.interfaces.ButtonDelegate;
import br.com.casadocodigo.bis.game.scenes.GameScene;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 *
 * No construtor habilitaremos o toque na tela, inicializando
 * os botões, e mantendo o link com a tela de jogo, pelo delegate.
 * Por fim, adicionaremos os botões criados na tela de jogo.
 */

public class GameButtons extends CCLayer implements ButtonDelegate {

    private Button leftControl;
    private Button rightControl;
    private Button shootButton;

    private GameScene delegate;

    public static GameButtons getGameButtons(){
        return new GameButtons();
    }

    public GameButtons(){
        // Habilita o toque na tela
        this.setIsTouchEnabled(true);

        // Cria os botões
        this.leftControl = new Button(Assets.LEFTCONTROL);
        this.rightControl = new Button(Assets.RIGHTCONTROL);
        this.shootButton = new Button(Assets.SHOOTBUTTON);

        // Configura as delegações
        this.leftControl.setDelegate(this);
        this.rightControl.setDelegate(this);
        this.shootButton.setDelegate(this);

        // Configura posições
        setButtonsPosition();

        // Adiciona os botões na tela
        addChild(leftControl);
        addChild(rightControl);
        addChild(shootButton);
    }

    @Override
    public void buttonClicked(Button sender) {
        if(sender.equals(this.leftControl)){
            Log.i("Button", "leftControl");
        }

        if(sender.equals(this.rightControl)){
            Log.i("Button", "rightControl");
        }

        if(sender.equals(this.shootButton)){
            Log.i("Button", "shootButton");
        }
    }

    public void setDelegate(GameScene gameScene) {
        this.delegate = gameScene;

    }

    private void setButtonsPosition(){
        // Posição dos botões
        leftControl.setPosition(screenResolution(CGPoint.ccp(40, 40)));
        rightControl.setPosition(screenResolution(CGPoint.ccp(100, 40)));
        shootButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() -40, 40)));
    }
}
