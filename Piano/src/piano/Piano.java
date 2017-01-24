package piano;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Piano extends JFrame implements KeyListener{
    private static int[] offset=new int[2];
    private static MidiChannel[]mc;
    private static boolean[] noteon=new boolean[128];
    private static boolean[] check=new boolean[4];
    private static int[] side=new int[128];
	private Renderer renderer;
    private JPanel keyPanel;
    public static Piano P;
    private static Recorder rc;
    public Piano() {
        try{
        	rc = new Recorder();
        	renderer=new Renderer();
            keys = new HashMap<>();
            pianoKeys = new Key[128];
            add(renderer);
    		setVisible(true);
            registerKeys();
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Piano");
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            mc = synthesizer.getChannels();
            mc[0].programChange(0,54);
            addKeyListener(this);
            pack();
            setSize(1000, 400);
            setResizable(false);
        }
        catch(MidiUnavailableException ex) {
        }
    }
    public void repaint(Graphics g) {
    	Color col=new Color(80,80,80);
    	g.setColor(col);
    	g.fillRect(20, 100, 940, 163);
    	Color[][] shade=new Color[2][2];
    	int[]left=new int[128];
    	int ptr=0;
    	shade[0][0]=new Color(255,255,255);
    	shade[1][0]=new Color(20,20,20);
    	shade[0][1]=new Color(250,250,150);
    	shade[1][1]=new Color(100,100,200);
    	int nextwhite=40;
    	int height=160;
    	for(int a=36;a<=108;a++){
    		g.setColor(Color.white);
    		if(a%12==0||a%12==2||a%12==4||a%12==5||a%12==7||a%12==9||a%12==11){
    			left[ptr]=nextwhite;
    			ptr++;
    			if(pianoKeys[a].isPressed())g.setColor(shade[0][1]);
    			else g.setColor(shade[0][0]);
    			g.fillRect(nextwhite,height,20,100);
    			nextwhite+=21;
    			g.setColor(Color.black);
    			if(a!=108)g.fillRect(nextwhite-1,height,1,100);
    		}
    		else{
    		}
    	}
    	int rtp=0;
    	for(int a=36;a<=108;a++){
    		g.setColor(Color.white);
    		if(a%12==0||a%12==2||a%12==4||a%12==5||a%12==7||a%12==9||a%12==11){
    			rtp++;
    		}
    		else{
    			if(pianoKeys[a].isPressed())g.setColor(shade[1][1]);
    			else g.setColor(shade[1][0]);
    			g.fillRect(left[rtp]-6, height, 11, 75);
    		}
    	}
	}
    public static int[] num=new int[128];
    public static void main(String[] args) {
    	if(args.length > 0) {
    		try {
    			File f = new File(args[0]);
    			System.out.println(f);
    			Sequencer S = MidiSystem.getSequencer();
    			S.setSequence(new BufferedInputStream(new FileInputStream(f)));
    			//This doesn't work
    			S.start();
    		}catch(Exception e){}
    	}
    	P = new Piano();
        P.setVisible(true);
        int[][] song;
        int[][] im=ImperialMarch();
        int[][] oc=OCanada();
        song=combine(im, oc, 300);
        //song=ImperialMarch();
        P.play(song, 280, 80);
        //P.play(song, 280, 100);
        //P.play(song,280,100);
        rc.start();
        P.free();
    }
    public void free(){
    	renderer.repaint();
    	while(true){
        	boolean[] pres=new boolean[128];
        	for(int a=60;a<=72;a++){
    			if(noteon[a-60]){
    				pres[a+offset[side[a]]]=true;
    			}
    		}
        	for(int a=0;a<128;a++){
        		if(pres[a]){
        			mc[0].noteOn(a, 100);
        			if(!rc.isPressed(a)) {
        				rc.press(a);
        				rc.record(a);
        			}
        			if(!pianoKeys[a].isPressed()){
    					pianoKeys[a].press();
    				}
        			rc.record(a);
        		}
        		else{
        			mc[0].noteOff(a);
        			if(rc.isPressed(a)) {
        				rc.press(a);
        				rc.record(a);
        			}
        			if(pianoKeys[a].isPressed()){
    					pianoKeys[a].depress();
    				}
        		}
        	}
        	renderer.repaint();
        	psit(100);
        }
    }
    public static int[][] ImperialMarch(){
    	int[][] s1=new int[100][128];
    	for(int a=0;a<128;a++){
    		num[a]=0;
    	}
        add(s1, 67, 8, 8);
        add(s1, 67, 16, 8);
        add(s1, 67, 24, 8);
        add(s1, 63, 32, 6);
        add(s1, 70, 38, 2);
        add(s1, 67, 40, 8);
        add(s1, 63, 48, 6);
        add(s1, 70, 54, 2);
        add(s1, 67, 56, 16);
        add(s1, 74, 72, 8);
        add(s1, 74, 80, 8);
        add(s1, 74, 88, 8);
        add(s1, 75, 96, 6);
        add(s1, 70, 102, 2);
        add(s1, 66, 104, 8);
        add(s1, 63, 112, 6);
        add(s1, 70, 118, 2);
        add(s1, 67, 120, 16);
        add(s1, 79, 136, 8);
        add(s1, 67, 144, 6);
        add(s1, 67, 150, 2);
        add(s1, 79, 152, 8);
        add(s1, 78, 160, 6);
        add(s1, 77, 166, 2);
        add(s1, 76, 168, 2);
        add(s1, 75, 170, 2);
        add(s1, 76, 172, 4);
        add(s1, 68, 179, 4);
        add(s1, 73, 184, 8);
        add(s1, 72, 192, 6);
        add(s1, 71, 198, 2);
        add(s1, 70, 200, 2);
        add(s1, 69, 202, 2);
        add(s1, 70, 204, 4);
        add(s1, 63, 211, 4);
        add(s1, 66, 216, 8);
        add(s1, 63, 224, 6);
        add(s1, 66, 230, 2);
        add(s1, 70, 232, 8);
        add(s1, 67, 240, 6);
        add(s1, 70, 246, 2);
        add(s1, 74, 248, 16);
    	return s1;
    }
    public static int[][] OCanada(){
    	int[][] s1=new int[100][128];
    	for(int a=0;a<128;a++){
    		num[a]=0;
    	}
    	add(s1, 69, 8, 16);
    	add(s1, 72, 24, 10);
    	add(s1, 72, 35, 4);
    	add(s1, 65, 40, 24);
    	add(s1, 67, 64, 8);
    	add(s1, 69, 72, 8);
    	add(s1, 70, 80, 8);
    	add(s1, 72, 88, 8);
    	add(s1, 74, 96, 8);
    	add(s1, 67, 104, 32);
    	add(s1, 69, 136, 16);
    	add(s1, 71, 152, 10);
    	add(s1, 71, 163, 4);
    	add(s1, 72, 168, 24);
    	add(s1, 74, 192, 8);
    	add(s1, 76, 200, 8);
    	add(s1, 76, 208, 8);
    	add(s1, 74, 216, 8);
    	add(s1, 74, 224, 8);
    	return s1;
    }
    public static int[][] combine(int[][] aa, int[][] bb, int length){
    	int[][] cc=new int[10000][128];
    	for(int a=0;a<128;a++){
    		num[a]=0;
    	}
    	boolean[][] on=new boolean[128][2];
    	boolean[] both=new boolean[128];
    	int[][] cur=new int[128][2];
    	for(int time=1;time<=length;time++){
    		for(int a=0;a<128;a++){
    			if(aa[cur[a][0]][a]==time){
    				on[a][0]=!on[a][0];
    				cur[a][0]++;
    			}
    		}
    		for(int a=0;a<128;a++){
    			if(bb[cur[a][1]][a]==time){
    				on[a][1]=!on[a][1];
    				cur[a][1]++;
    			}
    		}
    		for(int a=0;a<128;a++){
    			if((on[a][0]||on[a][1])&&!both[a]){
    				both[a]=true;
    				sadd(cc, a, time);
    			}
    			else if((!on[a][0]&&!on[a][1])&&both[a]){
    				both[a]=false;
    				sadd(cc, a, time);
    			}
    		}
    	}
    	return cc;
    }
    private void registerKeys() {
    	for(int a=67;a<=72;a++) side[a]=1;
    	for(int a=0;a<128;a++){
    		if(a%12==0||a%12==2||a%12==4||a%12==5||a%12==7||a%12==9||a%12==11){
    			pianoKeys[a] = new Key(Keys.WHITE, a);
    		}
    		else{
    			pianoKeys[a] = new Key(Keys.BLACK, a);
    		}
    	}
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
    private Synthesizer synthesizer ;
    Key[]pianoKeys;
    public void play(int[][] aa, int length, int pause){
    	boolean[] on=new boolean[128];
    	int[] cur=new int[128];
    	renderer.repaint();
    	for(int time=1;time<=length;time++){
    		for(int a=0;a<128;a++){
    			if(aa[cur[a]][a]==time){
    				while(aa[cur[a]][a]==time){
        				on[a]=!on[a];
        				cur[a]++;
        				if(on[a]){
            				mc[0].noteOn(a, 100);
            				if(!pianoKeys[a].isPressed()){
            					pianoKeys[a].press();
            				}
            			}
            			else{
            				mc[0].noteOff(a);
            				if(pianoKeys[a].isPressed()){
            					pianoKeys[a].depress();
            				}
            			}
        			}
    			}
    			else{
    				if(on[a]){
        				mc[0].noteOn(a, 100);
        				if(!pianoKeys[a].isPressed()){
        					pianoKeys[a].press();
        				}
        			}
        			else{
        				mc[0].noteOff(a);
        				if(pianoKeys[a].isPressed()){
        					pianoKeys[a].depress();
        				}
        			}
    			}
    		}
    		renderer.repaint();
    		psit(pause);
    	}
    }
    public static void add(int[][] aa, int ind, int start, int len){
    	sadd(aa, ind, start);
    	sadd(aa, ind, start+len);
    }
    public static void sadd(int[][] aa, int ind, int time){
    	aa[num[ind]][ind]=time;
    	num[ind]++;
    }
    public static void psit(int x) {
        try{
            Thread.sleep(x);
        }
        catch (Exception e){
                
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if(keys.containsKey(evt.getKeyCode())) {
        noteon[keys.get(evt.getKeyCode()) - 60]=true;
        }
        else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V){
                if(evt.getKeyCode()==KeyEvent.VK_C&&!check[0]){
                        check[0]=true;
                        if(offset[0]>=-12)offset[0]-=12;
                }
                else if(!check[1]){
                        check[1]=true;
                        if(offset[0]<=24)offset[0]+=12;
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
