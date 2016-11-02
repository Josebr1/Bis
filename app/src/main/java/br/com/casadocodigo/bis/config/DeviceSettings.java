package br.com.casadocodigo.bis.config;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by jose on 02/11/2016.
 */

public class DeviceSettings {

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

}
