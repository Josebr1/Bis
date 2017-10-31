package br.com.casadocodigo.bis.config;

import android.hardware.SensorManager;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by jose on 02/11/2016.
 *
 * -- Reponsável por acessar o CCDirector e retornar as medidas
 * e configurações do device.
 *
 */

public class DeviceSettings {

    private static SensorManager sensormanager;

    public static CGPoint screenResolution(CGPoint gcPoint) {
        return gcPoint;
    }

    public static float screenWidth() {
        return winSize().width;
    }

    public static float screenHeight() {
        return winSize().height;
    }

    private static CGSize winSize() {
        return CCDirector.sharedDirector().winSize();
    }

    public static SensorManager getSensormanager() {
        return sensormanager;
    }

    public static void setSensorManager(SensorManager sensormanager) {
        DeviceSettings.sensormanager = sensormanager;
    }
}
