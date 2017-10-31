package br.com.casadocodigo.bis.game.engines;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

import br.com.casadocodigo.bis.config.Assets;
import br.com.casadocodigo.bis.config.Runner;
import br.com.casadocodigo.bis.game.interfaces.MeteorsEngineDelegate;
import br.com.casadocodigo.bis.game.objects.Meteor;

/**
 * Created by jose on 02/11/2016.
 *
 * -- É importante que uma Engine saiba quando é o momento de colocar
 * um novo elemento no jogo. Muitas vezes, principalmente para objetos
 * inimigos, utilizamos números randômicos para definir a hora de colocar
 * um novo inimigo na tela.
 *
 * -- Vale citar que a engine é o código responsavel por manter o loop de
 * objetos, e com o Cocos2D, utilizamos métodos de agendamento para isso.
 * Ou seja, criaremos um 'schedule' para que a engine analise se deve ou
 * não atualizar e incluir um novo objeto inimigo na tela.
 */

public class MeteorsEngine extends CCLayer {
    private MeteorsEngineDelegate delegate;

    public MeteorsEngine(){
        this.schedule("meteorsEngine", 1.0f / 10f);
    }

    /**
     * Método chamado pelo construtor da classe
     * @param dt
     */
    public void meteorsEngine(float dt){
        // Sorte: 1 em 30 gera um novo meteoro!
        if (Runner.check().isGamePlaying() &&
                ! Runner.check().isGamePaused()) {
            if (new Random().nextInt(30) == 0) {
                this.getDelegate().createMeteor(
                        new Meteor(Assets.METEOR));
            }
        }
    }

    public void setDelegate(MeteorsEngineDelegate delegate){
        this.delegate = delegate;
    }

    public MeteorsEngineDelegate getDelegate(){
        return delegate;
    }

}
