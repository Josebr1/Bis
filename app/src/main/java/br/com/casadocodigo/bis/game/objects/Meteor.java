package br.com.casadocodigo.bis.game.objects;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import br.com.casadocodigo.bis.R;
import br.com.casadocodigo.bis.config.Runner;
import br.com.casadocodigo.bis.game.interfaces.MeteorsEngineDelegate;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by jose on 02/11/2016.
 */

public class Meteor extends CCSprite {
    private float x, y;
    private MeteorsEngineDelegate delegate;

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
        // pause
        if (Runner.check().isGamePlaying() &&
                ! Runner.check().isGamePaused()) {
            y -= 1;
            this.setPosition(screenResolution(CGPoint.ccp(x, y)));
        }
    }

    /**
     * Definimos que ele poderá ter um delegate, que será avisado das colisões
     * @param delegate
     */
    public void setDelegate(MeteorsEngineDelegate delegate){
        this.delegate = delegate;
    }

    /**
     * Será invocado quando houver a colisão.
     *
     * - Retiramos o link entre o obejto e a tela de jogo, cancelamos o agendamento
     * da atualização de posição e criamos as ações que juntas farão o efeito de sumir
     * do meteoro.
     */
    public void shooted(){
        // Adiciona o efeito de som de batida
        SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.bang);

        this.delegate.removeMeteor(this);

        // Para de fica chamando o update
        this.unschedule("update");

        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);

        CCCallFunc func = CCCallFunc.action(this, "removeMe");
        this.runAction(CCSequence.actions(s1, func));
    }

    /**
     * Será invocado logo após a animação acabar.
     */
    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }
}
