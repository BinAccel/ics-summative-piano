package piano;

/**
 *
 * @author roger
 */

import javax.sound.midi.*;
public class Instruments {
    
    /**
     * Get all available instruments on the computer
     * @return Returns all instruments as an array
     * @throws MidiUnavailableException 
     */
    public static Instrument[] getInstruments() throws MidiUnavailableException {
        return MidiSystem.getSynthesizer().getAvailableInstruments();
    }
    
    public static String[] getNames() throws MidiUnavailableException {
        Instrument[] tmp = getInstruments();
        String[] names = new String[tmp.length];
        for(int i=0;i<names.length;i++) {
            names[i] = tmp[i].getName();
        }
        return names;
    }
}
