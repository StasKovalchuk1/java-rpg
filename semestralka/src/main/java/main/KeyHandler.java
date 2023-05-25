package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * This class is for handling key events
 */
public class KeyHandler implements EventHandler<KeyEvent> {
    public boolean upPressed, downPressed, leftPressed, rightPressed, inventoryPressed, chestPressed,
            attackPressed, defendPressed, spacePressed;

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyEvent.getCode()){
                case W:
                    upPressed = true;
                    break;
                case S:
                    downPressed = true;
                    break;
                case A:
                    leftPressed = true;
                    break;
                case D:
                    rightPressed = true;
                    break;
                case K:
                    if (inventoryPressed) inventoryPressed = false;
                    else inventoryPressed = true;
                    break;
                case L:
                    chestPressed = true;
                    break;
                case N:
                    attackPressed = true;
                    break;
                case M:
                    defendPressed = true;
                    break;
                case SPACE:
                    spacePressed = true;
                    break;
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
            switch (keyEvent.getCode()){
                case W:
                    upPressed = false;
                    break;
                case S:
                    downPressed = false;
                    break;
                case A:
                    leftPressed = false;
                    break;
                case D:
                    rightPressed = false;
                    break;
                case L:
                    chestPressed = false;
                    break;
                case N:
                    attackPressed = false;
                    break;
                case M:
                    defendPressed = false;
                    break;
                case SPACE:
                    spacePressed = false;
                    break;
            }
        }
    }
}
