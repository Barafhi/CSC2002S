//package assignment_2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Model is separate from the view.
/**
 * Main class to initialize and run the game.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 *
 */
public class WordApp {
	// Shared variables.
	// Number of words falling at any point.
	static int noWords = 4;
	// Total words to fall in the game.
	static int totalWords;

	// Frame dimensions.
	static int frameX = 1000;
	static int frameY = 600;
	// Limit for the words falling.
	static int yLimit = 480;

	static WordDictionary dictionary = new WordDictionary();

	// Array of WordRecord objects
	static WordRecord[] words;
	static volatile boolean done; // must be volatile
	static Score score = new Score();

	static WordPanel w;

	/**
	 * Setup the GUI.
	 * 
	 * @param frameX
	 *            Width of the frame.
	 * @param frameY
	 *            Height of the frame.
	 * @param yLimit
	 *            Lowest point a word can fall before it is "missed".
	 */
	public static void setupGUI(int frameX, int frameY, int yLimit) {
		// Frame initiation and dimensions.
		JFrame frame = new JFrame("Typer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);

		// Panel to hold everything.
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
		g.setSize(frameX, frameY);

		// Word panel.
		w = new WordPanel(words, yLimit);
		w.setSize(frameX, yLimit + 100);
		g.add(w);

		// Score information and text entry.
		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));

		// Score information.
		JLabel caught = new JLabel("Caught: " + score.getCaught() + "    ");
		JLabel missed = new JLabel("Missed:" + score.getMissed() + "    ");
		JLabel scr = new JLabel("Score:" + score.getScore() + "    ");
		// Add the JLabels to the JPanel.
		txt.add(caught);
		txt.add(missed);
		txt.add(scr);

		// [snip]

		// Text entry.
		final JTextField textEntry = new JTextField("", 20);
		textEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = textEntry.getText();
				// Compare entered text to current words in the array.
				if (done)
					return;
				for (int i = 0; i < noWords; i++) {
					int x = words[i].getX();
					int y = words[i].getY();
					boolean reset = true;
					if (totalWords - score.getTotal() <= noWords)
						reset = false;
					if (words[i].matchWord(text, reset)) {
						score.caughtWord(text.length());
						w.explode(x, y);
						// ////////Scoring///////////////////////////////
						caught.setText("Caught: " + score.getCaught() + "    ");
						missed.setText("Missed:" + score.getMissed() + "    ");
						scr.setText("Score:" + score.getScore() + "    ");
						if (score.getTotal() == totalWords) {
							done = true;
							stopGame();
						}
						break;
					}
				}
				textEntry.setText("");
				textEntry.requestFocus();
			}
		});

		// Add the textEntry to the JPanel.
		txt.add(textEntry);
		txt.setMaximumSize(txt.getPreferredSize());
		g.add(txt);

		// Buttons.
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		// Start button.
		JButton startButton = new JButton("Start");
		;
		// Add the listener to the JButton to handle the "pressed" event.
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Should start the game, initiate the threads.
				restartGame();
				// Return focus to the text entry field.
				textEntry.requestFocus();
				// System.out.println("Start pushed");
			}
		});
		// End button.
		JButton endButton = new JButton("End");
		;
		// Add the listener to the JButton to handle the "pressed" event.
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// End the game.
				stopGame();
				// System.out.println("End pushed");
			}
		});
		JButton quitButton = new JButton("Quit");
		;
		// Add the listener to the JButton to handle the "pressed" event.
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// Add the buttons to the JPanel.
		b.add(startButton);
		b.add(endButton);
		b.add(quitButton);
		// Add the button panel.
		g.add(b);

		// Center window on screen.
		frame.setLocationRelativeTo(null);
		// Add contents to window.
		frame.add(g);
		frame.setContentPane(g);
		// frame.pack(); // don't do this - packs it into small space
		frame.setVisible(true);
	}

	/**
	 * Get a dictionary of words from a file and returns them as a String array.
	 * 
	 * @param filename
	 *            The name of the file with the dictionary of words.
	 * @return The String array dictionary of words. Returns null if the file
	 *         could not be read.
	 */
	public static String[] getDictFromFile(String filename) {
		String[] dictionaryStrings = null;
		try {
			Scanner dictionaryReader = new Scanner(
					new FileInputStream(filename));
			// Number of words in the file.
			int dictionaryLength = dictionaryReader.nextInt();
			// System.out.println("read '" + dictLength+"'");

			dictionaryStrings = new String[dictionaryLength];
			for (int i = 0; i < dictionaryLength; i++) {
				// Add the next word to the dictionary array.
				dictionaryStrings[i] = new String(dictionaryReader.next());
				// System.out.println(i+ " read '" + dictStr[i]+"'"); //for
				// checking
			}
			dictionaryReader.close();
		} catch (IOException e) {
			System.err.println("Problem reading file \"" + filename
					+ "\". Default dictionary will be used.");
		}
		return dictionaryStrings;

	}

	/**
	 * @param args
	 *            0 - Total words to fall. 1 - Number of words falling at any
	 *            point. 2 - File of words.
	 */
	public static void main(String[] args) {
		// For compiling in IDE:
		// totalWords = 10;
		// noWords = 3;
		// String[] fileDictionary = getDictFromFile("");

		// Deal with command line arguments.
		// The total words to fall.
		totalWords = Integer.parseInt(args[0]);
		// The total words falling at any point.
		noWords = Integer.parseInt(args[1]);

		// Dealing with problematic arguments.
		if (totalWords == 0) {
			totalWords = 1;
			System.err.println("Total number of words zero. Adjusting to 1.");
		}
		if (noWords == 0) {
			noWords = 1;
			System.err.println("Number of words zero. Adjusting to 1.");
		}
		if (totalWords < 0) {
			totalWords *= -1;
			System.err.println("Total number of words negative. Adjusting to "
					+ totalWords + ".");
		}
		if (noWords < 0) {
			noWords *= -1;
			System.err.println("Number of words negative. Adjusting to "
					+ noWords + ".");
		}
		if (totalWords < noWords) {
			noWords = totalWords;
			System.err
					.println("Total number of words to fall less than number of words falling at one time. Adjusting number of words to "
							+ noWords + ".");
		}

		// File of words.
		String[] fileDictionary = getDictFromFile(args[2]);
		// If the file was read correctly, set the dictionary to the new one.
		// Else leave the default dictionary.
		if (fileDictionary != null)
			dictionary = new WordDictionary(fileDictionary);
		// Set the class dictionary for the words.
		WordRecord.dictionary = dictionary;
		// Shared array of current words.
		words = new WordRecord[noWords];

		// [snip]

		setupGUI(frameX, frameY, yLimit);

		// Start WordPanel thread - for redrawing animation.

		// Split the frame into equal portions for each word falling.
		int x_inc = (int) frameX / noWords;
		// Initialize shared array of blank words.
		for (int i = 0; i < noWords; i++) {
			words[i] = new WordRecord("", i * x_inc, yLimit);
		}

	}

	private static void restartGame() {
		w.requestStop();
		score.resetScore();
		done = false;
		for (int i = 0; i < noWords; i++) {
			words[i].resetWord();
		}
		// Start the game thread.
		Thread wThread = new Thread(w);
		wThread.start();
	}

	private static void stopGame() {
		w.requestStop();
		// Deal with scoring etc.
		JOptionPane.showMessageDialog(null, "Your score:\n" + score.getScore());
	}

}
