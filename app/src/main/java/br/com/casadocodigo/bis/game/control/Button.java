package br.com.casadocodigo.bis.game.control;

import android.view.MotionEvent;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * Created by jose on 02/11/2016.
 */

public class Button extends CCLayer {
    private CCSprite buttonImage;
    private ButtonDelegate delegate;

    public Button(String buttonImage) {
        this.setIsTouchEnabled(true);
        this.buttonImage = CCSprite.sprite(buttonImage);
        addChild(this.buttonImage);
    }

    /**
     * Método da classe Button que define quem deverá ser avisado.
     * @param sender
     */
    public void setDelegate(ButtonDelegate sender) {
        this.delegate = sender;
    }

    /**
     * Para dizer que o próprio objeto recebe as notificações de toque
     */
    @Override
    protected void registerWithTouchDispatcher() {
        CCTouchDispatcher.sharedDispatcher()
                .addTargetedDelegate(this, 0, false);
    }

    /**
     * CGRect.containsPoint que verifica se o local tocado (touchLocation)
     * está dentro da "caixa" que a imagem do menu forma (buttonImage.getBoudingBox())
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
        touchLocation = CCDirector.sharedDirector()
                .convertToGL(touchLocation);
        touchLocation = this.convertToNodeSpace(touchLocation);

        // Verifica toque no botão
        if (CGRect.containsPoint(
                this.buttonImage.getBoundingBox(), touchLocation)) {
            delegate.buttonClicked(this);

        }

        return true;
    }
}
