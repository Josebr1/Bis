package br.com.casadocodigo.bis.game.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.screens.ScreenBackground;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 *
 * Classe que inicializa objetos no jogo, que coloca objetos na tela,
 * porém o comportamento de cada um deles será representado individulmente
 * em cada classe correspondente.
 *
 * Algumas das responsabilidades:
 *
 * .Iniciar a tela do game e organizar as camadas
 * .Adicionar objetos como player, inimigos e botões a essas camadas
 * .Inicializar cada um desses objetos
 * .Checar colisões entre objetos
 */

public class GameScene extends CCLayer {
    private ScreenBackground background;

    private GameScene(){
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(
                        CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)
                )
        );
        this.addChild(this.background);
    }

    public static CCScene createGame(){
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }
}
