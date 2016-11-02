package br.com.casadocodigo.bis.game.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.screens.ScreenBackground;

/**
 * Created by jose on 02/11/2016.
 *
 * -- Criar telas com o CCLayer do Cocos2D é criar telas pensando em camadas que
 * se sobrepõem. Essas camadas são transparente, a mesno que definidas de outra forma,
 * e quando colocadas uma sobre as outras definem a tela final.
 *
 * -- Nesta classe, utilizaremos um segundo componente do Cocos2D. Para instanciar uma tela
 * no fremework, utilizamos a classe CCScene, que é devolvida já pronta para utilizarmos
 * quando invocamos o método node().
 *
 * -- Scenes com elas, conseguimos inicializar telas do jogo. Um jogo pode ter quantas Scenes
 * forem necessárias, porém apenas uma estará ativa por vez.
 *
 */

public class TitleScreen extends CCLayer {

    private ScreenBackground background;

    public TitleScreen(){
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                CGPoint.ccp(
                        CCDirector.sharedDirector().winSize().width / 2.0f,
                        CCDirector.sharedDirector().winSize().height / 2.0f
                )
        );
        this.addChild(this.background);
    }

    /**
     * Prepara a tela para utilização e posicionamento dos elementos.
     * @return
     */
    public CCScene scene(){
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return  scene;
    }


}
