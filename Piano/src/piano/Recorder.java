package piano;

public class Recorder {
	private int[][]rc;
	private boolean isRc;
	private long stT;
	private int[]num;
	
	public Recorder() {
		stT = System.currentTimeMillis();
		isRc = true;
		rc = new int [1000][128];
		num = new int[128];
	}
	
	public int[][] getRecording() {
		return rc;
	}
	
	public boolean isRecording() {
		return isRc;
	}
	
	public void record(int note, boolean start) {
		rc[num[note]][note] = (int) ((System.currentTimeMillis() - stT) / 1000);
		num[note]++;
	}
}
