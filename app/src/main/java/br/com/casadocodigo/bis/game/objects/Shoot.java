package br.com.casadocodigo.bis.game.objects;

import android.util.Log;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.game.interfaces.ShootEngineDelegate;

import static br.com.casadocodigo.bis.config.DeviceSettings.screenResolution;

/**
 * Created by jose on 02/11/2016.
 *
 *
 */

public class Shoot extends CCSprite {
    private ShootEngineDelegate delegate;

    float positionX, positionY;

    public Shoot(float positionX, float positionY){
        super(Assets.SHOOT);
        this.positionX = positionX;
        this.positionY = positionY;
        setPosition(this.positionX, this.positionY);
        this.schedule("update");
    }

    /**
     * Esse método será executado pelo Cocos2D a cada interação.
     * O Framework manda como parâmetro um tempo de execução, para que possa
     * ser analisado se algo deve ser ou não executado desde a última vez
     * que ele invocou esse método.
     *
     * OBS: Não será utilizado por causa da lógica do jogo.
     * @param dt
     */
    public void update(float dt){
        positionY += 2;
        this.setPosition(screenResolution(CGPoint.ccp(positionX, positionY)));
    }

    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }

    /**
     * Método que será utilizado para verificar que o tipo está funcionando.
     */
    public void start(){
        Log.i("Start", "Shot moving!");
    }

    public void explode(){
        // Remove do array
        this.delegate.removeShoot(this);

        // Para o agendamento
        this.unschedule("update");

        // Cria efeitos
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);

        // Função a ser executada após efeito
        CCCallFunc func = CCCallFunc.action(this, "removeMe");

        // Roda efeito
        this.runAction(CCSequence.actions(s1, func));
    }

    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }
}
