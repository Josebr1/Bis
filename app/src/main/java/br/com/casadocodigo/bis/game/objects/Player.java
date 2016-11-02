package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.nodes.CCSprite;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.interfaces.ShootEngineDelegate;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 *
 *
 */

public class Player extends CCSprite {

    private float positionX = screenWidth() / 2;
    private float positionY = 50;

    private ShootEngineDelegate delegate;

    public Player(){
        super(Assets.NAVA);
        setPosition(positionX, positionY);
    }

    /**
     * O player terá o método shoot() que captura o posicionamento da
     * nave e chama a engine de criação do tiro.
     */
    public void shoot(){
        delegate.createShoot(new Shoot(positionX, positionY));
    }

    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }

}
