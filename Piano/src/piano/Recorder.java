package piano;

public class Recorder {
	private int[][]rc;
	private boolean isRc;
	private long stT;
	private int[]num;
	private boolean[]prs;
	
	public Recorder() {
		rc = new int [1000][128];
		num = new int[128];
		prs = new boolean[128];
	}
	
	public void start() {
		stT = System.currentTimeMillis();
		isRc = true;
	}
	
	public void stop() {
		isRc = false;
	}
	
	public int[][] getRecording() {
		return rc;
	}
	
	public boolean isRecording() {
		return isRc;
	}
	
	public boolean isPressed(int i) {
		return prs[i];
	}
	
	public void press(int i) {
		prs[i] = !prs[i];
	}
	
	public void record(int note) {
		rc[num[note]][note] = (int) ((System.currentTimeMillis() - stT) / 1000);
		num[note]++;
	}
}
