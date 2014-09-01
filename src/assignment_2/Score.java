//package assignment_2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class to store the score as well as the number of words caught and missed.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 *
 */
public class Score {
	// Number of words missed (hit the bottom before typed).
	private AtomicInteger missedWords;
	// Number of words caught (typed before hitting the bottom).
	private AtomicInteger caughtWords;
	// Current score for the game.
	private AtomicInteger gameScore;

	/**
	 * Create a new Score with values for the caught words, missed words and
	 * score set to 0.
	 */
	Score() {
		missedWords = new AtomicInteger();
		caughtWords = new AtomicInteger();
		gameScore = new AtomicInteger();
	}

	// All getters and setters must be synchronized.
	// Decided to use atomic variables instead, with some synchronization where
	// needed.

	/**
	 * Get the number of words missed.
	 * 
	 * @return Integer number of missed words.
	 */
	public int getMissed() {
		return missedWords.get();
	}

	/**
	 * Get the number of words caught.
	 * 
	 * @return Integer number of caught words.
	 */
	public int getCaught() {
		return caughtWords.get();
	}

	/**
	 * Get the total number of fallen words, missed and caught.
	 * 
	 * @return Integer number of words fallen.
	 */
	public synchronized int getTotal() {
		return (missedWords.get() + caughtWords.get());
	}

	/**
	 * Get the score.
	 * 
	 * @return The current game score.
	 */
	public int getScore() {
		return gameScore.get();
	}

	/**
	 * Increment the number of words missed by one.
	 */
	public void missedWord() {
		missedWords.getAndIncrement();
	}

	/**
	 * Increment the number of caught words by one and update the score by the
	 * length of the word.
	 * 
	 * @param length
	 *            The length of the caught word.
	 */
	public synchronized void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	/**
	 * Reset the score. Set the caught words, missed words and score to 0.
	 */
	public synchronized void resetScore() {
		caughtWords = new AtomicInteger();
		missedWords = new AtomicInteger();
		gameScore = new AtomicInteger();
	}
}
