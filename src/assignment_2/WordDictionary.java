package assignment_2;

/**
 * A class to store a dictionary of Strings and allowing for random Strings to
 * be fetched.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 */
public class WordDictionary {
	// Size of the dictionary.
	int size;
	// String array for the dictionary of words. Initial array of words as a
	// default.
	static String[] dictionary = { "litchi", "banana", "apple", "mango",
			"pear", "orange", "strawberry", "cherry", "lemon", "apricot",
			"peach", "guava", "grape", "kiwi", "quince", "plum", "prune",
			"cranberry", "blueberry", "rhubarb", "fruit", "grapefruit",
			"kumquat", "tomato", "berry", "boysenberry", "loquat", "avocado" }; // default
																				// dictionary

	/**
	 * Create a new WordDictionary by copying the String array into the
	 * WordDictionary String array.
	 */
	WordDictionary(String[] inputDictionary) {
		size = inputDictionary.length;
		this.dictionary = new String[size];
		for (int i = 0; i < size; i++) {
			this.dictionary[i] = inputDictionary[i];
		}

	}

	/**
	 * Create a new WordDictionary using the default dictionary of words.
	 */
	WordDictionary() {
		size = dictionary.length;
	}

	/**
	 * Get a random word from the dictionary.
	 */
	public synchronized String getNewWord() {
		int wdPos = (int) (Math.random() * size);
		return dictionary[wdPos];
	}

}
