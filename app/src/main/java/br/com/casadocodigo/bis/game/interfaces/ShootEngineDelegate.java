package br.com.casadocodigo.bis.game.interfaces;

import br.com.casadocodigo.bis.game.objects.Shoot;

/**
 * Created by jose on 02/11/2016.
 *
 * Interface responsavel por colocar o tiro no jogo.
 * Essa responsabilidade não é da tela de jogo e nem do proprio tiro.
 * Diz a engine o que fazer.
 *
 */

public interface ShootEngineDelegate {
    void createShoot(Shoot shoot);

    void removeShoot(Shoot shoot);
}
