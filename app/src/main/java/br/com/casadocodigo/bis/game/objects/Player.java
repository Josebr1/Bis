package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.nodes.CCSprite;

import br.com.casadocodigo.bis.config.Assets;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 */

public class Player extends CCSprite {

    private float positionX = screenWidth() / 2;
    private float positionY = 50;

    public Player(){
        super(Assets.NAVA);
        setPosition(positionX, positionY);
    }

    /*public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }*/

}
