package br.com.casadocodigo.bis.game.scenes;

import android.util.Log;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.bis.R;
import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.control.GameButtons;
import br.com.casadocodigo.bis.game.engines.MeteorsEngine;
import br.com.casadocodigo.bis.game.interfaces.MeteorsEngineDelegate;
import br.com.casadocodigo.bis.game.interfaces.ShootEngineDelegate;
import br.com.casadocodigo.bis.game.objects.Meteor;
import br.com.casadocodigo.bis.game.objects.Player;
import br.com.casadocodigo.bis.game.objects.Score;
import br.com.casadocodigo.bis.game.objects.Shoot;
import br.com.casadocodigo.bis.screens.ScreenBackground;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 * <p>
 * Classe que inicializa objetos no jogo, que coloca objetos na tela,
 * porém o comportamento de cada um deles será representado individulmente
 * em cada classe correspondente.
 * <p>
 * Algumas das responsabilidades:
 * <p>
 * .Iniciar a tela do game e organizar as camadas
 * .Adicionar objetos como player, inimigos e botões a essas camadas
 * .Inicializar cada um desses objetos
 * .Checar colisões entre objetos
 */

public class GameScene extends CCLayer implements MeteorsEngineDelegate, ShootEngineDelegate {
    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;
    private CCLayer playerLayer;
    private Player player;
    private CCLayer shootsLayer;
    private List shootsArray;
    private List playersArray;
    private CCLayer scoreLayer;
    private Score score;

    private GameScene() {
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(
                        CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)
                )
        );
        this.addChild(this.background);

        /* Adicionar o delegate, fazendo o link entre as telas */
        GameButtons gameButtonsLayer = GameButtons.getGameButtons();
        gameButtonsLayer.setDelegate(this);
        this.addChild(gameButtonsLayer);

        // Adicionando objeto (inimigos) na tela.
        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);

        // Adicionando o player
        this.playerLayer = CCLayer.node();
        this.addChild(this.playerLayer);

        // Shoot - Criando as camadas
        this.shootsLayer = CCLayer.node();
        this.addChild(this.shootsLayer);

        // Score
        this.scoreLayer = CCLayer.node();
        this.addChild(this.scoreLayer);

        this.setIsTouchEnabled(true);

        this.addGameObjects();

        // sons
        SoundEngine.sharedEngine().playSound(
                CCDirector.sharedDirector().getActivity(), R.raw.music, true);

        // Cache som
        this.preloadCache();
    }

    /**
     * Para colocar um som no cache devemos iniciá-lo já no início do game. Criaremos
     então um método com essa responsabilidade na classe GameScene chamado
     preloadCache. Esse método fará o cache dos 3 sons que estamos utilizando até
     aqui.
     */
    private void preloadCache(){
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.shoot);
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.bang);
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.over);
    }

    /**
     * Método que conterá a inicialização dos objetos do jogo.
     */
    private void addGameObjects() {
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine();

        this.player = new Player();
        this.playerLayer.addChild(this.player);
        // Captura o acelerometro
        this.player.catchAccelerometer();

        this.playersArray = new ArrayList();
        this.playersArray.add(this.player);

        // Score
        this.score = new Score();
        this.scoreLayer.addChild(this.score);

        //* Array que adiciona os tiros
        // Nesse momento e ideal para o fechamento do link do delegate entre
        // tiro e tela de jogo.*/
        this.shootsArray = new ArrayList();
        this.player.setDelegate(this);
    }

    /**
     * onEnter() ele é invocado pelo Cocos2D assim que a tela
     * do game está pronta para orquestrar os objetos do jogo.
     * Ele será a porta de entrada do jogo.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        this.schedule("checkHits");

        this.startEngines();
    }

    /**
     * Método chamado em tempo de execução, para detectar colisões.
     * @param dt
     */
    public void checkHits(float dt) {
        this.checkRadiusHitsOfArray(this.meteorsArray, this.shootsArray, this, "meteoroHit");

        this.checkRadiusHitsOfArray(this.meteorsArray, this.playersArray, this, "playerHit");
    }

    /**
     * Os dois primeiros são arrays de objetos a serem verificados.
     * Ou seja, se queremos checar se os tiros estão colidindo com os
     * meteoros, passaremos esse dois array. Outro ponto importente é
     * passar uma referência da tela do jogo, no caso a GameSCence. Precisamos
     * disso para pode executar algum método caso a colisão seja detectada,
     * porém como diversas colisões podem ser detectadas, como nave com tiro
     * ou tipo com meteoro, isso será decidido em tempo de execução.
     * E exatamente por isso que precisamos, por último, de um parâmetro
     * a mais, que recebe o nome do método a ser executado no caso de a colisão
     * acontecer.
     *
     * O que teremos então é uma verificação de cada elemento do primeiro array, com
     * cada elemento do segundo array. Caso a detecção, faremos um tratamento.
     *
     * E importante perceber que a decisão de qual método rodar após detectar
     * a colisão é em tempo de execução, e preciso invocar o reflection.
     *
     * @param array1
     * @param array2
     * @param gameScene
     * @param hit
     * @return
     */
    private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1,
                                           List<? extends CCSprite> array2,
                                           GameScene gameScene,
                                           String hit) {
        boolean result = false;
        for (int i = 0; i < array1.size(); i++) {
            // Pega objeto do primeiro array
            CGRect rect1 = getBoarders(array1.get(i));
            for (int j = 0; j < array2.size(); j++) {
                // Pega objeto do segundo array
                CGRect rect2 = getBoarders(array2.get(j));
                // Verifica colisão
                if (CGRect.intersects(rect1, rect2)) {
                    System.out.println("Colision Detected: " + hit);
                    result = true;

                    Method method;
                    try {
                        method = GameScene.class.getMethod(hit, CCSprite.class, CCSprite.class);
                        method.invoke(gameScene, array1.get(i), array2.get(j));
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * Método que receberá um Sprite e devolverá um retângulo
     * que conterá as bordas do elemento. Para isso, utilizaremos
     * um método que existe nos próprio Sprites chamado getBoundingBox().
     * Esse método devolve um tipo CGRect, também do Cocos2D, que representa
     * os contornos da figura mapeados em forma de retangulo.
     *
     * Para trabalhar com essas informações, precisamos tambem saber a posição
     * do elemento na tela, e então utilizaremos outro tipo chamado CGPoint.
     * Com esse método, teremos todas as coordenadas do posicionamento do objeto
     * a ser analisado.
     *
     * @param object
     * @return
     */
    public CGRect getBoarders(CCSprite object) {
        CGRect rect = object.getBoundingBox();
        //  Log.e("JOGO", "rect x: " + rect.origin.x);
        // Log.e("JOGO", "rect y: " + rect.origin.y);
        //  Log.e("JOGO", "rect width: " + rect.size.width);
        //   Log.e("JOGO", "rect height: " + rect.size.height);
        //CGPoint GLpoint = rect.origin;
        //CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width, rect.size.height);
        // Log.e("JOGO", "GLrect x: " + GLrect.origin.x);
        // Log.e("JOGO", "GLrect y: " + GLrect.origin.y);
        // Log.e("JOGO", "GLrect width: " + GLrect.size.width);
        // Log.e("JOGO", "GLrect height: " + GLrect.size.height);
        return rect;
    }

    /**
     * Por enquanto simplesmente adicionaremos o memeteorsEngine
     * e setamos o delegate como this, para sermos avisados dos
     * novos meteoros:
     */
    private void startEngines() {
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    @Override
    public void createMeteor(Meteor meteor, float x, float y, float vel, double ang, int vl) {

    }

    /**
     * Toda vez que criamos um novo Meteor, queremos que ele avise a própria
     * GameScene de que ele foi removido. Em outras palavras, a GameScene será
     * o delegate do Meteor.
     */
    @Override
    public void createMeteor(Meteor meteor) {
        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        meteorsArray.add(meteor);
    }

    /**
     * Shoot() que chama o player. Precisamos disso por um fato muito importante
     * que é o posicionamento inicial do tiro.O tiro deve sair da nave.
     *
     * @return
     */
    public boolean shoot() {
        player.shoot();
        return true;
    }

    /**
     * Nesse momento, implementaremos a interface ShootEngineDelegate na GameScene.
     * Dessa forma, a tela de jogo saberá o que fazer quando for requisitada para atira.
     * A interface obriga a criação do método createShoot(). Nese, um novo tiro, que
     * é recebido como parâmetro, é adicionado à camada e ao array de tiros. Alèm
     * disso, chama o método start() da classe Shoot, permitindo que ela controle o
     * que for necessário lá dentro.
     *
     * @param shoot
     */
    @Override
    public void createShoot(Shoot shoot) {
        this.shootsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shoot.start();
        this.shootsArray.add(shoot);
    }

    public void moveLeft() {
        player.moveLeft();
    }

    public void moveRight() {
        player.moveRight();
    }

    /**
     * Adicione o meteoroHit()
     * @param meteor
     * @param shoot
     */
    public void meteoroHit(CCSprite meteor, CCSprite shoot){
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();
        this.score.increase();
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        this.meteorsArray.remove(meteor);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        this.shootsArray.remove(shoot);
    }

    public void playerHit(CCSprite meteor, CCSprite player){
        ((Meteor) meteor).shooted();
        ((Player) player).explode();
        CCDirector.sharedDirector()
                .replaceScene(new GameOverScreen().scene());
    }

    public static CCScene createGame() {
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }

    /**
     * Para que essa classe possa ser vista no jogo, a classe GameScene deve estar ciente
     e inicializá-la. Para isso, teremos o método startFinalScreen que faz a transição
     para a tela final.v
     */
    public void startFinalScreen() {
        CCDirector.sharedDirector().replaceScene(new FinalScreen().scene());
        Log.i("FinalScreen", "OK");
    }
}
