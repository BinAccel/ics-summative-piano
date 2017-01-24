package piano;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.Timer;

public class Piano extends JFrame implements KeyListener{
    public static int[] offset=new int[2];
    public static MidiChannel[]mc;
    public static boolean[] noteon=new boolean[128];
    public static boolean[] check=new boolean[4];
    public static int[] side=new int[128];
    public Piano() {
        try{
            keys = new HashMap<>();
            pianoKeys = new Key[13];
            registerKeys();
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Piano");
            Container content = getContentPane();
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            mc = synthesizer.getChannels();
            mc[0].programChange(0,54);
            addKeyListener(this);
            JPanel keyPanel = new JPanel();
            OverlayLayout ol = new OverlayLayout(keyPanel);
            keyPanel.setLayout(ol);
            pianoKeys[0].setAlignmentX(0.0f);
            pianoKeys[1].setAlignmentX(0.9f);
            keyPanel.add(pianoKeys[0]);
            keyPanel.add(pianoKeys[1]);
            //ol.addLayoutComponent("Key 2", pianoKeys[1]);
            content.add(keyPanel, BorderLayout.WEST);
            //content.add(pianoKeys[0]);
            keyPanel.revalidate();
            pack();
            setSize(1000, 600);
            setResizable(false);
        } catch(MidiUnavailableException ex) {
        }
    }
    
    public static void main(String[] args) {
        new Piano().setVisible(true);
        while(true){
        	boolean[] pres=new boolean[128];
        	for(int a=60;a<=72;a++){
    			if(noteon[a]){
    				pres[a+offset[side[a]]]=true;
    			}
    		}
        	for(int a=0;a<128;a++){
        		if(pres[a])
                            mc[0].noteOn(a, 100);
        		else
                            mc[0].noteOff(a);
        	}
        	psit(100);
        }
    }
    
    private void registerKeys() {
    	for(int a=67;a<=72;a++){
    		side[a]=1;
    	}
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
        pianoKeys[12] = new Key(Keys.WHITE, 72);
    }
    
    private HashMap<Integer, Integer>keys;
    private Synthesizer synthesizer ;
    Key[]pianoKeys;
    public static void psit(int x) {
        try {
            Thread.sleep(x);
        }
        catch (Exception e){
                
        }
    }
    @Override
    public void keyPressed(KeyEvent evt) {
        if(keys.containsKey(evt.getKeyCode()))
        noteon[pianoKeys[keys.get(evt.getKeyCode()) - 60].press()]=true;
        else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V){
                if(evt.getKeyCode()==KeyEvent.VK_C&&!check[0]){
                        check[0]=true;
                        if(offset[0]>=-24)offset[0]-=12;
                }
                else if(!check[1]){
                        check[1]=true;
                        if(offset[0]<=36)offset[0]+=12;
                }
        }
        else if(evt.getKeyCode()==KeyEvent.VK_N||evt.getKeyCode()==KeyEvent.VK_M){
                if(evt.getKeyCode()==KeyEvent.VK_N&&!check[2]){
                        check[2]=true;
                        if(offset[1]>=-24)offset[1]-=12;
                }
                else if(!check[3]){
                        check[3]=true;
                        if(offset[1]<=36)offset[1]+=12;
                }
        }
    }
    @Override
    public void keyReleased(KeyEvent evt) {
		if(keys.containsKey(evt.getKeyCode()))
    		noteon[pianoKeys[keys.get(evt.getKeyCode()) - 60].depress()]=false;
		else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V||evt.getKeyCode()==KeyEvent.VK_N||evt.getKeyCode()==KeyEvent.VK_M){
			if(evt.getKeyCode()==KeyEvent.VK_C){
				check[0]=false;
			}
			if(evt.getKeyCode()==KeyEvent.VK_V){
				check[1]=false;
			}
			if(evt.getKeyCode()==KeyEvent.VK_N){
				check[2]=false;
			}
			if(evt.getKeyCode()==KeyEvent.VK_M){
				check[3]=false;
			}
		}
    }
	@Override
	public void keyTyped(KeyEvent evt) {
	}
}
