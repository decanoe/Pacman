package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardController implements KeyListener {
    public static ArrayList<KeyListener> controllers = new ArrayList<KeyListener>();
    static KeyboardController instance = null;

    public static KeyListener get_KeyListener() {
        if (instance == null) instance = new KeyboardController();
        return instance;
    }

    public static void add_controller(KeyListener controller) {
        controllers.add(controller);
    }
    public static void remove_controller(KeyListener controller) {
        controllers.remove(controller);
    }

    public void keyPressed(KeyEvent e) {
        for (KeyListener controller : controllers) {
            controller.keyPressed(e);
        }
    }
    public void keyReleased(KeyEvent e) {
        for (KeyListener controller : controllers) {
            controller.keyReleased(e);
        }
    }
    public void keyTyped(KeyEvent e) {
        for (KeyListener controller : controllers) {
            controller.keyTyped(e);
        }
    }
}
