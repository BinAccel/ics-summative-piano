package piano;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;
import javax.swing.*;

public class Piano extends JFrame{

    public Piano() {
        registerKeys();
        
    }
    
    public static void main(String[] args) {
        new Piano().setVisible(true);
    }
    
    private void play(int i) {
        
    }
    
    private void registerKeys() {
        keys.put(KeyEvent.VK_A, 60); //C
        keys.put(KeyEvent.VK_W, 61); //C#
        keys.put(KeyEvent.VK_S, 62); //D
        keys.put(KeyEvent.VK_E, 63); //D#
        keys.put(KeyEvent.VK_D, 64); //E
        keys.put(KeyEvent.VK_F, 65); //F
        keys.put(KeyEvent.VK_T, 66); //F#
        keys.put(KeyEvent.VK_J, 67); //G
        keys.put(KeyEvent.VK_U, 68); //G#
        keys.put(KeyEvent.VK_K, 69); //A
        keys.put(KeyEvent.VK_I, 70); //A#
        keys.put(KeyEvent.VK_L, 71); //B
        keys.put(KeyEvent.VK_SEMICOLON, 72); //C
    }
    
    private HashMap<Integer, Integer>keys;
    private Sequencer sequencer;
}
