package piano;

import java.awt.Container;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.sound.midi.*;
import javax.swing.*;

public class Piano extends JFrame{

    public Piano() {
        try{
            keys = new HashMap<>();
            pianoKeys = new Key[12];
            registerKeys();
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Piano");
            Container content = getContentPane();
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            MidiChannel[]channels = synthesizer.getChannels();
            addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent evt) {
                    if(keys.containsKey(evt.getKeyCode()))
                    channels[0].noteOn(pianoKeys[keys.get(evt.getKeyCode()) - 60].press(), 100);
                }

                public void keyReleased(KeyEvent evt) {
                    if(keys.containsKey(evt.getKeyCode()))
                    channels[0].noteOff(pianoKeys[keys.get(evt.getKeyCode())-60].depress());
                }
            });
            content.add(pianoKeys[0]);
            content.add(pianoKeys[1]);
            //content.add(new JLabel("HI!"));
            pack();
            setSize(1000, 600);
            setResizable(false);
        } catch(MidiUnavailableException ex) {
            Logger.getLogger(Piano.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        new Piano().setVisible(true);
    }
    
    private void registerKeys() {
        keys.put(KeyEvent.VK_A, 60); //C
        pianoKeys[0] = new Key(Keys.WHITE, 60);
        keys.put(KeyEvent.VK_W, 61); //C#
        pianoKeys[1] = new Key(Keys.BLACK, 61);
        keys.put(KeyEvent.VK_S, 62); //D
        pianoKeys[2] = new Key(Keys.WHITE, 62);
        keys.put(KeyEvent.VK_E, 63); //D#
        pianoKeys[3] = new Key(Keys.BLACK, 63);
        keys.put(KeyEvent.VK_D, 64); //E
        pianoKeys[4] = new Key(Keys.WHITE, 64);
        keys.put(KeyEvent.VK_F, 65); //F
        pianoKeys[5] = new Key(Keys.WHITE, 65);
        keys.put(KeyEvent.VK_T, 66); //F#
        pianoKeys[6] = new Key(Keys.BLACK, 66);
        keys.put(KeyEvent.VK_J, 67); //G
        pianoKeys[7] = new Key(Keys.WHITE, 67);
        keys.put(KeyEvent.VK_U, 68); //G#
        pianoKeys[8] = new Key(Keys.BLACK, 68);
        keys.put(KeyEvent.VK_K, 69); //A
        pianoKeys[9] = new Key(Keys.WHITE, 69);
        keys.put(KeyEvent.VK_I, 70); //A#
        pianoKeys[10] = new Key(Keys.BLACK, 70);
        keys.put(KeyEvent.VK_L, 71); //B
        pianoKeys[11] = new Key(Keys.WHITE, 71);
        keys.put(KeyEvent.VK_SEMICOLON, 72); //C
    }
    
    private HashMap<Integer, Integer>keys;
    private Synthesizer synthesizer ;
    Key[]pianoKeys;
}
