//package assignment_2;

/**
 * A class to update the WordRecords in the game.
 * 
 * @author Michael Evans
 *
 */
public class WordUpdater implements Runnable {
	private volatile boolean running = false;
	// Array of WordRecords.
	private WordRecord[] words;
	// Individual last update times for each word.
	private volatile long[] lastLoopTime;
	// Number of words.
	private int noWords;

	WordUpdater(WordRecord[] words) {
		this.words = words;
		noWords = words.length;
		lastLoopTime = new long[noWords];
	}

	@Override
	public void run() {
		running = true;
		for (int i = 0; i < noWords; i++) {
			lastLoopTime[i] = System.currentTimeMillis();
		}
		while (running) {
			// Update word positions.
			for (int i = 0; i < noWords; i++) {
				if (words[i].falling())
					lastLoopTime[i] = words[i].dropByLastTime(lastLoopTime[i]);
			}
		}
	}

	/**
	 * Sets a flag to stop the running thread.
	 */
	public void requestStop() {
		running = false;
	}
}
