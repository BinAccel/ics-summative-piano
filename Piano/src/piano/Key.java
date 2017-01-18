package piano;

import java.io.*;
import javax.imageio.*;
import javax.swing.*;
/**
 *
 * @author roger
 */
public class Key {
    
    //Someday I will learn how to make this extend JLabel
    //But not today
    
    /**
     * The type of key
     * 
     */
    private Keys type;
    private boolean pressed;
    private int keycode;
    /**
     * The key, stored as a label
     * <p>
     * This value exists only because I have no idea how to make the class extend <tt>JLabel</tt>
     * @deprecated 
     */
    private JLabel label;
    
    public Key(Keys type, int keycode) {
        depress();
        this.type = type;
        this.keycode = keycode;
    }
    
    /**
     * Returns the key as a JLabel
     * @return Returns the key's representation as a <tt>JLabel</tt> so it can be easily added to the screen
     * @deprecated 
     */
    public JLabel getLabel() {
        return label;
    }
    
    /**
     * Presses the current key
     * @return <tt>keycode</tt>
     */
    public int press() {
        pressed = true;
        try {
            if(null != type) switch (type) {
                case BLACK:
                    label = new JLabel(new ImageIcon(ImageIO.read(ImgResources.BLACK_KEY_PRESSED)));
                    break;
                case WHITE:
                    label = new JLabel(new ImageIcon(ImageIO.read(ImgResources.WHITE_KEY_PRESSED)));
                    break;
                default:
                    System.err.printf("Unknown key type detected! Now exiting...");
                    System.exit(-1);
                        break;
            }
        } catch (IOException ioe) {
            System.err.printf("There was an error retrieving the resource '%s'. Now Exiting...", type == Keys.BLACK? "black_key_pressed.png": "white_key_pressed.png");
            System.exit(-1);
        }
        return keycode;
    }
    
    public int depress() {
        pressed = false;
        try {
            if(null != type) switch (type) {
                case BLACK:
                    label = new JLabel(new ImageIcon(ImageIO.read(ImgResources.BLACK_KEY)));
                    break;
                case WHITE:
                    label = new JLabel(new ImageIcon(ImageIO.read(ImgResources.WHITE_KEY)));
                    break;
                default:
                    System.err.printf("Unknown key type detected! Now exiting...");
                    System.exit(-1);
                        break;
            }
        } catch (IOException ioe) {
            System.err.printf("There was an error retrieving the resource '%s'. Now Exiting...", type == Keys.BLACK? "black_key.png": "white_key.png");
            System.exit(-1);
        }
        return keycode;
    }
}

enum Keys {
    BLACK, WHITE;
}

final class ImgResources {
    
    public static File BLACK_KEY = new File("black_key.png");
    public static File WHITE_KEY = new File("white_key.png");
    public static File BLACK_KEY_PRESSED = new File("black_key_pressed.png");
    public static File WHITE_KEY_PRESSED = new File("white_key_pressed.png");
}