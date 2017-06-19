package cubo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class IsKeyPressed {
    private static volatile boolean lPressed = false;
    private static volatile boolean rPressed = false;
    private static volatile boolean cPressed = false;
    private static volatile boolean bPressed = false;
    private static volatile boolean oPressed = false;
    
    public static boolean isLPressed() {
        synchronized (IsKeyPressed.class) {
            return lPressed;
        }
    }
    public static boolean isRPressed() {
        synchronized (IsKeyPressed.class) {
            return rPressed;
        }
    }
    public static boolean isCPressed() {
        synchronized (IsKeyPressed.class) {
            return cPressed;
        }
    }
    public static boolean isBPressed() {
        synchronized (IsKeyPressed.class) {
            return bPressed;
        }
    }
    public static boolean isOPressed() {
        synchronized (IsKeyPressed.class) {
            return oPressed;
        }
    }
    
    public static void main(String args[]) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (IsKeyPressed.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_L) {
                            lPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_R) {
                            rPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_C) {
                            cPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_B) {
                            bPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_O) {
                            oPressed = true;
                        }
                        break;

                    /*case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        }
                        break;*/
                    }
                    return false;
                }
            }
        });
    }
}
