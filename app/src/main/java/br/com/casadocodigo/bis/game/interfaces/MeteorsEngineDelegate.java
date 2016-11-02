package br.com.casadocodigo.bis.game.interfaces;

import br.com.casadocodigo.bis.game.objects.Meteor;

/**
 * Created by jose on 02/11/2016.
 */

public interface MeteorsEngineDelegate {
    void createMeteor(Meteor meteor, float x, float y, float vel, double ang, int vl);

    /**
     * obrigará a tela do jogo a ter um método para receber os objetos
     * criados por essa Engine e colocá-los na tela.
     * @param meteor
     */
    void createMeteor(Meteor meteor);

    /**
     * Para que ela receba a notificação de quando um meteoro colidiu
     * @param meteor
     */
    void removeMeteor(Meteor meteor);
}
