package br.com.casadocodigo.bis.config;

/**
 * Created by antonio on 10/30/17.
 */

public class Runner {
    static Runner runner = null;

    private static boolean isGamePlaying;
    private static boolean isGamePaused;

    private Runner(){
    }

    public static Runner check(){
        if (runner!=null){
            runner = new Runner();
        }
        return runner;
    }

    public static boolean isGamePlaying() {
        return isGamePlaying;
    }
    public static boolean isGamePaused() {
        return isGamePaused;
    }

    public static void setGamePlaying(boolean isGamePlaying) {
        Runner.isGamePlaying = isGamePlaying;
    }
    public static void setGamePaused(boolean isGamePaused) {
        Runner.isGamePaused = isGamePaused;
    }
}
