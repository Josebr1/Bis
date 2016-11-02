package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 */

public class Meteor extends CCSprite {
    private float x, y;

    /**
     * Inicialmente, cada meteoro nasce no topo da tela (é o valor
     * de screenHeight), e uma posição x randômica.
     * @param image
     */
    public Meteor(String image){
        super(image);
        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    /**
     * Aqui mais uma vez o framework nos ajuda. Para que cada
     * frame seja renderizado durante o jogo, e a posição do objeto mude]
     * com o passar do tempo, o Cocos2D nos permite escolher um método
     * que será invocado de tempo em tempo. Isso será definido no start,
     * fazendo schedule("nomeDoMetodo").
     */
    public void start(){
        this.schedule("update");
    }

    /**
     * dt for maior que o de 1 frame, deveriamos descer o meteoro mais que 1
     * pixel, fazendo a regra de 3.
     * @param dt
     */
    public void update(float dt){
        y -= 1;
        this.setPosition(screenResolution(CGPoint.ccp(x, y)));
    }
}
