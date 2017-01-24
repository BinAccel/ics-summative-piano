package piano;

import java.awt.Dimension;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
/**
 *
 * @author roger
 */
public class Key extends JLabel{
    
    /**
     * The type of key
     * 
     */
    private Keys type;
    private boolean pressed;
    private int keycode;
    
    public Key(Keys type, int keycode) {
        this.type = type;
        this.keycode = keycode;
        depress();
    }
    
    /**
     * Presses the current key
     * @return <tt>keycode</tt>
     */
    public int press() {
        pressed = true;
        ImageIcon ii = null;
        try {
            if(null != type) switch (type) {
                case BLACK:
                    ii = new ImageIcon(ImageIO.read(ImgResources.BLACK_KEY_PRESSED));
                    break;
                case WHITE:
                    ii = new ImageIcon(ImageIO.read(ImgResources.WHITE_KEY_PRESSED));
                    break;
                default:
                    System.err.printf("Unknown key type detected! Now exiting...");
                    System.exit(-1);
                        break;
            }
            setIcon(ii);
            setIconTextGap(0);
            setBorder(null);
            setText(null);
            setSize(ii.getImage().getWidth(null), ii.getImage().getHeight(null));
        } catch (IOException ioe) {
            System.err.printf("There was an error retrieving the resource '%s'. Now Exiting...", type == Keys.BLACK? "black_key_pressed.png": "white_key_pressed.png");
            System.exit(-1);
        }
        return keycode;
    }
    
    public int depress() {
        pressed = false;
        try {
            ImageIcon ii = null;
            if(null != type) switch (type) {
                case BLACK:
                    ii = new ImageIcon(ImageIO.read(ImgResources.BLACK_KEY));
                    break;
                case WHITE:
                    ii = new ImageIcon(ImageIO.read(ImgResources.WHITE_KEY));
                    break;
                default:
                    System.err.printf("Unknown key type detected! Now exiting...");
                    System.exit(-1);
                        break;
            }
            setIcon(ii);
            setIconTextGap(0);
            setBorder(null);
            setText(null);
            setSize(ii.getImage().getWidth(null), ii.getImage().getHeight(null));
        } catch (IOException ioe) {
            System.err.printf("There was an error retrieving the resource '%s'. Now Exiting...", type == Keys.BLACK? "black_key.png": "white_key.png");
            System.exit(-1);
        }
        return keycode;
    }
    
    public boolean isPressed() {
    	return pressed;
    }
    
    public Dimension getPreferredSize()
    {
        return getSize();
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