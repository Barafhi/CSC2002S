package assignment_2;

/**
 * A class to store the score as well as the number of words caught and missed.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 *
 */
public class Score {
	// //////////////////////////////////////////////
	// Could make atomic?
	// //////////////////////////////////////////////

	// Number of words missed (hit the bottom before typed).
	private int missedWords;
	// Number of words caught (typed before hitting the bottom).
	private int caughtWords;
	// Current score for the game.
	private int gameScore;

	/**
	 * Create a new Score with values for the caught words, missed words and
	 * score set to 0.
	 */
	Score() {
		missedWords = 0;
		caughtWords = 0;
		gameScore = 0;
	}

	// //////////////////////////////////////////////
	// All getters and setters must be synchronized
	// //////////////////////////////////////////////

	/**
	 * Get the number of words missed.
	 * 
	 * @return Integer number of missed words.
	 */
	public int getMissed() {
		return missedWords;
	}

	/**
	 * Get the number of words caught.
	 * 
	 * @return Integer number of caught words.
	 */
	public int getCaught() {
		return caughtWords;
	}

	/**
	 * Get the total number of fallen words, missed and caught.
	 * 
	 * @return Integer number of words fallen.
	 */
	public int getTotal() {
		return (missedWords + caughtWords);
	}

	/**
	 * Get the score.
	 * 
	 * @return The current game score.
	 */
	public int getScore() {
		return gameScore;
	}

	/**
	 * Increment the number of words missed by one.
	 */
	public void missedWord() {
		missedWords++;
	}

	/**
	 * Increment the number of caught words by one and update the score by the
	 * length of the word.
	 * 
	 * @param length
	 *            The length of the caught word.
	 */
	public void caughtWord(int length) {
		caughtWords++;
		gameScore += length;
	}

	/**
	 * Reset the score. Set the caught words, missed words and score to 0.
	 */
	public void resetScore() {
		caughtWords = 0;
		missedWords = 0;
		gameScore = 0;
	}
}
