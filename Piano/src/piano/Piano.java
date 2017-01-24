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
    public JPanel keyPanel;
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
            keyPanel = new JPanel();
            OverlayLayout ol = new OverlayLayout(keyPanel);
            keyPanel.setLayout(ol);
            pianoKeys[5].setAlignmentX(0.0f);
            pianoKeys[5].setAlignmentY(0.0f);
            pianoKeys[4].setAlignmentX(0.5f);
            pianoKeys[4].setAlignmentY(0.0f);
            pianoKeys[3].setAlignmentX(0.0f);
            pianoKeys[3].setAlignmentY(0.0f);
            pianoKeys[2].setAlignmentX(0.5f);
            pianoKeys[2].setAlignmentY(0.0f);
            pianoKeys[1].setAlignmentX(0.5f);
            pianoKeys[1].setAlignmentY(0.0f);
            pianoKeys[0].setAlignmentX(1.0f);
            pianoKeys[0].setAlignmentY(0.0f);
            keyPanel.add(pianoKeys[1]);
            keyPanel.add(pianoKeys[3]);
            keyPanel.add(pianoKeys[0]);
            keyPanel.add(pianoKeys[2]);
            keyPanel.add(pianoKeys[4]);
            content.add(keyPanel, BorderLayout.WEST);
            keyPanel.revalidate();
            pack();
            setSize(1000, 600);
            setResizable(false);
        } catch(MidiUnavailableException ex) {
        }
    }
    public static int[] num=new int[128];
    public static void main(String[] args) {
        Piano P = new Piano();
        P.setVisible(true);
        int[][] im=ImperialMarch();
        P.play(im, 250);
        while(true){
        	boolean[] pres=new boolean[128];
        	for(int a=60;a<=72;a++){
    			if(noteon[a-60]){
    				pres[a+offset[side[a]]]=true;
    			}
    		}
        	for(int a=0;a<128;a++){
        		if(pres[a]) mc[0].noteOn(a, 100000);
        		else mc[0].noteOff(a);
        	}
        	psit(100);
        }
    }
    public static int[][] ImperialMarch(){
    	int[][] s1=new int[10000][128];
    	for(int a=0;a<128;a++){
    		num[a]=0;
    	}
        add(s1, 67, 8, 7);
        add(s1, 67, 16, 7);
        add(s1, 67, 24, 7);
        add(s1, 63, 32, 5);
        add(s1, 70, 38, 2);
        add(s1, 67, 40, 7);
        add(s1, 63, 48, 5);
        add(s1, 70, 54, 2);
        add(s1, 67, 56, 15);
        add(s1, 74, 72, 7);
        add(s1, 74, 80, 7);
        add(s1, 74, 88, 7);
        add(s1, 75, 96, 5);
        add(s1, 70, 102, 2);
        add(s1, 66, 104, 7);
        add(s1, 63, 112, 5);
        add(s1, 70, 118, 2);
        add(s1, 67, 120, 15);
        add(s1, 79, 136, 7);
        add(s1, 67, 144, 5);
        add(s1, 67, 150, 2);
        add(s1, 79, 152, 7);
        add(s1, 78, 160, 5);
        add(s1, 77, 166, 2);
        add(s1, 76, 168, 7);
        add(s1, 68, 176, 7);
        add(s1, 73, 184, 7);
        add(s1, 72, 192, 5);
        add(s1, 71, 198, 2);
        add(s1, 70, 200, 7);
        add(s1, 63, 208, 7);
        add(s1, 66, 216, 7);
        add(s1, 63, 224, 5);
        add(s1, 66, 230, 2);
        add(s1, 70, 232, 7);
        add(s1, 67, 240, 5);
        add(s1, 70, 246, 2);
        add(s1, 74, 248, 15);
    	return s1;
    }
    private void registerKeys() {
    	for(int a=67;a<=72;a++) side[a]=1;
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
    public void play(int[][] aa, int length){
    	boolean[] on=new boolean[128];
    	int[] cur=new int[128];
    	for(int time=1;time<=length;time++){
    		for(int a=0;a<128;a++){
    			if(aa[cur[a]][a]==time){
    				on[a]=!on[a];
    				cur[a]++;
    			}
    			if(on[a]){
    				mc[0].noteOn(a, 100);
    				if(!pianoKeys[keyMod(a)].isPressed()) pianoKeys[keyMod(a)].press();
    				keyPanel.revalidate();
    			}
    			else{
    				mc[0].noteOff(a);
    				if(pianoKeys[keyMod(a)].isPressed()) pianoKeys[keyMod(a)].depress();
    				keyPanel.revalidate();
    			}
    		}
    		psit(100);
    	}
    }
    
    private int keyMod(int i) {
    	return ((i - 60) % pianoKeys.length + pianoKeys.length) % pianoKeys.length;
    }
    
    public static void add(int[][] aa, int ind, int start, int len){
    	aa[num[ind]][ind]=start;
    	num[ind]++;
    	aa[num[ind]][ind]=start+len;
    	num[ind]++;
    }
    public static void psit(int x) {
        try {
            Thread.sleep(x);
        }
        catch (Exception e){
                
        }
    }
    @Override
    public void keyPressed(KeyEvent evt) {
        if(keys.containsKey(evt.getKeyCode())) {
        noteon[keys.get(evt.getKeyCode()) - 60]=true;
        pianoKeys[keys.get(evt.getKeyCode()) - 60].press();
        }
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
		if(keys.containsKey(evt.getKeyCode())) {
			noteon[keys.get(evt.getKeyCode()) - 60]=false;
			pianoKeys[keys.get(evt.getKeyCode()) - 60].depress();
		}
		else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V||evt.getKeyCode()==KeyEvent.VK_N||evt.getKeyCode()==KeyEvent.VK_M){
			if(evt.getKeyCode()==KeyEvent.VK_C) check[0]=false;
			if(evt.getKeyCode()==KeyEvent.VK_V) check[1]=false;
			if(evt.getKeyCode()==KeyEvent.VK_N) check[2]=false;
			if(evt.getKeyCode()==KeyEvent.VK_M) check[3]=false;
		}
    }
	@Override
	public void keyTyped(KeyEvent evt) {
	}
}
