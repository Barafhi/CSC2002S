//package assignment_2;

/**
 * A class to hold words in the game.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 *
 */
public class WordRecord {
	// Word string.
	private String text;
	// Coordinates for location.
	private int x;
	private int y;
	// The maximum Y value a word can reach before being missed.
	private int maxY;
	// Has the word started falling?
	private boolean falling;
	// Speed of the word (in pixels/second).
	private int fallingSpeed;
	// Defines the range of speeds allowed (in pixels/second).
	private static int maxSpeed = 60;
	private static int minSpeed = 20;
	// Carry over time for updates for smoother updating.
	private long updateTimeAccumulator = 0;
	// Dictionary of words.
	public static WordDictionary dictionary;

	/**
	 * Create a new WordRecord with default values. X and Y coordinates set to
	 * 0, text set to an empty String, maxY set to 300, dropped set to false and
	 * the falling speed set to a random speed within the limits.
	 */
	WordRecord() {
		this.text = "";
		this.x = 0;
		this.y = 0;
		this.maxY = 300;
		this.fallingSpeed = createSpeed();
		this.falling = true;
	}

	/**
	 * Create a new WordRecord with the given text and with other values as
	 * default. X and Y coordinates set to 0, maxY set to 300, dropped set to
	 * false and the falling speed set to a random speed within the limits.
	 * 
	 * @param text
	 *            The word/text to set.
	 */
	WordRecord(String text) {
		this.text = text;
		this.x = 0;
		this.y = 0;
		this.maxY = 300;
		this.fallingSpeed = createSpeed();
		this.falling = true;
	}

	/**
	 * Create a new WordRecord with the given text, X coordinate, maxY value and
	 * with other values as default. Y coordinate set to 0, dropped set to false
	 * and the falling speed set to a random speed within the limits.
	 * 
	 * @param text
	 *            The word/text to set.
	 * @param x
	 *            X coordinate.
	 * @param maxY
	 *            Maximum Y.
	 */
	WordRecord(String text, int x, int maxY) {
		this.text = text;
		this.x = x;
		this.y = 0;
		this.maxY = maxY;
		this.fallingSpeed = createSpeed();
		this.falling = true;
	}

	/**
	 * Set the Y coordinate of the WordRecord.
	 * 
	 * @param y
	 *            Y coordinate.
	 */
	public synchronized void setY(int y) {
		if (y > maxY) {
			y = maxY;
			this.falling = false;
		}
		this.y = y;
	}

	/**
	 * Set the X coordinate of the WordRecord.
	 * 
	 * @param x
	 *            X coordinate.
	 */
	public synchronized void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the text of the WordRecord.
	 * 
	 * @param text
	 *            The new word/text to set.
	 */
	public synchronized void setWord(String text) {
		this.text = text;
	}

	/**
	 * Get the word of the WordRecord.
	 * 
	 * @return String word.
	 */
	public synchronized String getWord() {
		return text;
	}

	/**
	 * Get the X coordinate of the WordRecord.
	 * 
	 * @return X coordinate.
	 */
	public synchronized int getX() {
		return x;
	}

	/**
	 * Get the Y coordinate of the WordRecord.
	 * 
	 * @return Y coordinate.
	 */
	public synchronized int getY() {
		return y;
	}

	/**
	 * Get the falling speed of the WordRecord.
	 * 
	 * @return The falling speed.
	 */
	public synchronized int getSpeed() {
		return fallingSpeed;
	}

	/**
	 * Set the coordinates of the WordRecord.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/**
	 * Reset the Y coordinate of the WordRecord.
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * Reset the WordRecord. Sets the Y coordinate to the start of the frame,
	 * gets a new word from the dictionary, marks it as falling and gets a new
	 * falling speed.
	 */
	public synchronized void resetWord() {
		resetPos();
		text = dictionary.getNewWord();
		fallingSpeed = createSpeed();
		falling = true;
	}

	/**
	 * Retire the WordRecord. Sets the Y coordinate to the start of the frame,
	 * sets an empty String as its word, marks it as not falling and sets the
	 * falling speed to 0.
	 */
	private synchronized void retireWord() {
		resetPos();
		text = "";
		fallingSpeed = 0;
		falling = false;
	}

	/**
	 * Check if the WordRecord matches the String parameter.
	 * 
	 * @param text
	 *            Text to compare.
	 * @param reset
	 *            Should the word be reset (true) or retired (false).
	 */
	public synchronized boolean matchWord(String text, boolean reset) {
		// Don't match words that arn't falling.
		if (!falling)
			return false;
		if (text.equals(this.text)) {
			if (reset)
				resetWord();
			else
				retireWord();
			return true;
		}
		return false;
	}

	/**
	 * Check if the WordRecord has been missed.
	 * 
	 * @param reset
	 *            Should the word be reset (true) or retired (false).
	 */
	public synchronized boolean missWord(boolean reset) {
		if (!falling)
			return false;
		if (y >= maxY) {
			if (reset)
				resetWord();
			else
				retireWord();
			return true;
		}
		return false;
	}

	/**
	 * Move the WordRecord down by the increment value.
	 */
	public synchronized void drop(int increment) {
		setY(y + increment);
	}

	/**
	 * Move the WordRecord down based on the amount of time that has passed
	 * since the last drop.
	 * 
	 * @param delta
	 *            Time in milliseconds.
	 */
	public synchronized long dropByLastTime(long lastLoop) {
		long currentTime = System.currentTimeMillis();
		updateTimeAccumulator += currentTime - lastLoop;
		int pixels = (int) (((double) fallingSpeed * 0.001) * updateTimeAccumulator);
		updateTimeAccumulator -= ((long) (pixels * 1000.0 / fallingSpeed));
		setY(y + pixels);
		return currentTime;
	}

	/**
	 * Return true if the WordRecord is falling.
	 */
	public synchronized boolean falling() {
		return falling;
	}

	/**
	 * Set if the word is falling.
	 * 
	 * @param falling
	 *            Is the word falling.
	 */
	public synchronized void setFalling(boolean falling) {
		this.falling = falling;
	}

	/**
	 * Create a random speed between the limits.
	 * 
	 * @return Integer speed in pixels/second.
	 */
	private static int createSpeed() {
		return (int) ((Math.random() * (maxSpeed - minSpeed) + minSpeed));
	}
}
