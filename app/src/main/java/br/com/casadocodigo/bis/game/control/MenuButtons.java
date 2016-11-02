package br.com.casadocodigo.bis.game.control;

import android.util.Log;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 */

public class MenuButtons extends CCLayer implements ButtonDelegate{
    private Button playButton;
    private Button highsCoredButton;
    private Button helpButton;
    private Button soundButton;

    public MenuButtons(){
        setIsTouchEnabled(true);

        playButton = new Button(Assets.PLAY);
        highsCoredButton = new Button(Assets.HIGHSCORE);
        helpButton = new Button(Assets.HELP);
        soundButton = new Button(Assets.SOUND);

        // Coloca Botões na posição correta
        setButtonsPosition();

        /* Informação de que o delegate dos botões deve avisar a própria classe
         * MenuButtons, já que é ela que tem o método buttonClicked */
        playButton.setDelegate(this);
        highsCoredButton.setDelegate(this);
        helpButton.setDelegate(this);
        soundButton.setDelegate(this);

        addChild(playButton);
        addChild(highsCoredButton);
        addChild(helpButton);
        addChild(soundButton);
    }


    /**
     * Método que seta as posições dos botões na tela
     */
    private void setButtonsPosition(){
        // Buttons Positions
        playButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 250))
        );

        highsCoredButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 300))
        );

        helpButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 350))
        );

        soundButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2 - 100, screenHeight() - 420))
        );
    }

    /**
     * Método que faz a verificação do toque
     * @param sender
     */
    @Override
    public void buttonClicked(Button sender) {
        if(sender.equals(playButton)){
            Log.i("BIS", "Play");
        }

        if(sender.equals(highsCoredButton)){
            Log.i("BIS", "Highs Core");
        }

        if(sender.equals(helpButton)){
            Log.i("BIS", "Help");
        }

        if(sender.equals(soundButton)){
            Log.i("BIS", "Sound");
        }
    }

}
