//package assignment_2;

import java.util.concurrent.CopyOnWriteArrayList;

public class WordUpdater implements Runnable {

	private WordRecord[] words;
	private volatile boolean running;
	private volatile long[] lastLoopTime;
	private int noWords;
	private int maxY;

	public WordUpdater(WordRecord[] words, int maxY) {
		running = true;
		this.words = words;
		noWords = words.length;
		this.maxY = maxY;
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
