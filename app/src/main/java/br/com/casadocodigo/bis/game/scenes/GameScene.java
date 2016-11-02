package br.com.casadocodigo.bis.game.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.engines.MeteorsEngine;
import br.com.casadocodigo.bis.game.interfaces.MeteorsEngineDelegate;
import br.com.casadocodigo.bis.game.objects.Meteor;
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

public class GameScene extends CCLayer implements MeteorsEngineDelegate{
    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;

    private GameScene(){
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(
                        CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)
                )
        );
        this.addChild(this.background);

        // Adicionando objeto (inimigos) na tela.
        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);
        this.addGameObjects();
    }

    public static CCScene createGame(){
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }

    @Override
    public void createMeteor(Meteor meteor, float x, float y, float vel, double ang, int vl) {

    }

    @Override
    public void createMeteor(Meteor meteor) {
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        meteorsArray.add(meteor);
    }

    /**
     * Método que conterá a inicialização dos objetos do jogo.
     */
    private void addGameObjects(){
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine();
    }

    /**
     * onEnter() ele é invocado pelo Cocos2D assim que a tela
     * do game está pronta para orquestrar os objetos do jogo.
     * Ele será a porta de entrada do jogo.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        this.startEngines();
    }

    /**
     * Por enquanto simplesmente adicionaremos o memeteorsEngine
     * e setamos o delegate como this, para sermos avisados dos
     * novos meteoros:
     */
    private void startEngines(){
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }
}
