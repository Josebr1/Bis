package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;

import br.com.casadocodigo.bis.R;
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
    private float positionY = 120;

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

    public void moveLeft(){
        if(positionX > 30){
            positionX -= 10;
        }
        setPosition(positionX, positionY);
    }

    public void moveRight(){
        if(positionX < screenWidth() - 30){
            positionX += 10;
        }
        setPosition(positionX, positionY);
    }

    public void explode(){
        // Adiciona o efeito de som: batida
        SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
        SoundEngine.sharedEngine().pauseSound();

        // Para o agendamento
        this.unschedule("update");

        // Cria efeitos
        // Cria efeitos
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);


        // Roda efeito
        this.runAction(CCSequence.actions(s1));
    }

}
