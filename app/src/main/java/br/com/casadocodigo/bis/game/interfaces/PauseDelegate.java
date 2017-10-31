package br.com.casadocodigo.bis.game.interfaces;

/**
 * Created by antonio on 10/30/17.
 */

public interface PauseDelegate {
    public void resumeGame();

    public void quitGame();

    public void pauseGameAndShowLayer();
}
