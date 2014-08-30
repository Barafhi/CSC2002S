package assignment_2;

/**
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

	// The
	private int maxY;
	// Has the word started falling?
	private boolean dropped;

	// Speed of the word.
	private int fallingSpeed;
	// Defines the range of speeds allowed.
	private static int maxWait = 1500;
	private static int minWait = 100;

	// Dictionary of words.
	public static WordDictionary dictionary;

	/**
	 * Create a new WordRecord with default values. X and Y coordinates set to
	 * 0, text set to an empty String, maxY set to 300, dropped set to false and
	 * the falling speed set to a random speed within the limits.
	 */
	WordRecord() {
		text = "";
		x = 0;
		y = 0;
		maxY = 300;
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
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
		this();
		this.text = text;
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
		this(text);
		this.x = x;
		this.maxY = maxY;
	}

	// ///////////////////////////////////////////////
	// all getters and setters must be synchronized
	// ///////////////////////////////////////////////

	/**
	 * Set the Y coordinate of the WordRecord.
	 * 
	 * @param y
	 *            Y coordinate.
	 */
	public synchronized void setY(int y) {
		if (y > maxY) {
			y = maxY;
			dropped = true;
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
		String a = "";
		a.equals("");
	}

	/**
	 * Reset the Y coordinate of the WordRecord.
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * Reset the WordRecord. Sets the Y coordinate to the start of the frame,
	 * gets a new word from the dictionary, marks it as not dropped and gets a
	 * new falling speed.
	 */
	public synchronized void resetWord() {
		resetPos();
		text = dictionary.getNewWord();
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
		// System.out.println(getWord() + " falling speed = " + getSpeed());
	}

	/**
	 * Check if the WordRecord matches the String parameter.
	 */
	public synchronized boolean matchWord(String text) {
		// System.out.println("Matching against: "+text);
		if (text.equals(this.text)) {
			resetWord();
			return true;
		} else
			return false;
	}

	/**
	 * Move the WordRecord down by the increment value.
	 */
	public synchronized void drop(int increment) {
		setY(y + increment);
	}

	/**
	 * Return true if the WordRecord has been dropped.
	 */
	public synchronized boolean dropped() {
		return dropped;
	}

}
