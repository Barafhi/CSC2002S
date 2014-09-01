//package assignment_2;

/**
 * A class to control the scoring in the game.
 * 
 * @author Michael Evans
 *
 */
public class Controller implements Runnable {
	private static volatile boolean running = false;
	private WordRecord[] words;
	private int totalWords;
	private int noWords;
	private Score score;
	private WordPanel w;

	Controller(WordRecord[] words, int totalWords, Score score, WordPanel w) {
		this.words = words;
		this.totalWords = totalWords;
		this.noWords = words.length;
		this.score = score;
		this.w = w;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			missWord();
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Match the words against the String parameter.
	 * 
	 * @param text
	 *            String to compare.
	 */
	public synchronized void matchWord(String text) {
		for (int i = 0; i < noWords; i++) {
			// Coordinates for explosion.
			int x = words[i].getX();
			int y = words[i].getY();
			// Should a new word be made?
			boolean reset = true;
			if (totalWords - score.getTotal() <= noWords)
				reset = false;
			// Match the words.
			if (words[i].matchWord(text, reset)) {
				w.explode(x, y);
				score.caughtWord(text.length());
				return;
			}
		}
	}

	/**
	 * Check if the words have been missed.
	 */
	public synchronized void missWord() {
		for (int i = 0; i < noWords; i++) {
			// Coordinates for explosion.
			int x = words[i].getX();
			int y = words[i].getY();
			// Should a new word be made?
			boolean reset = true;
			if (totalWords - score.getTotal() <= noWords)
				reset = false;
			// Miss the words.
			if (words[i].missWord(reset)) {
				w.implode(x, y);
				score.missedWord();
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
