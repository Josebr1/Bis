package br.com.casadocodigo.bis;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import br.com.casadocodigo.bis.config.DeviceSettings;
import br.com.casadocodigo.bis.game.scenes.TitleScreen;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Definindo orientação como landscape
        setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        );

        // Configura a tela
        CCGLSurfaceView glSurfaceView = new CCGLSurfaceView(this);
        setContentView(glSurfaceView);
        CCDirector.sharedDirector().attachInView(glSurfaceView);

        // Configura CCDirector
        CCDirector.sharedDirector().setScreenSize(320, 480);

        // Abre tela principal
        CCScene scene = new TitleScreen().scene();
        CCDirector.sharedDirector().runWithScene(scene);

        // sensor
        configSensorManager();
    }

    private void configSensorManager(){
        SensorManager sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        DeviceSettings.setSensorManager(sensorManager);
    }
}
