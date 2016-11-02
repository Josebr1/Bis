package br.com.casadocodigo.bis.game.interfaces;

import br.com.casadocodigo.bis.game.control.Button;

/**
 * Created by jose on 02/11/2016.
 *
 * Corresponde ao onClickListener do android
 *
 */

public interface ButtonDelegate {
    void buttonClicked(Button sender);
}
