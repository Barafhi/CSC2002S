package assignment_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
	public static volatile boolean done;
	private WordRecord[] words;
	private int noWords;
	private int maxY;

	/**
	 * Repaint the game screen with update word positions.
	 */
	public void paintComponent(Graphics g) {
		// Dimensions of the frame.
		int width = this.getWidth();
		int height = this.getHeight();

		// Clear the frame in order to repaint the words.
		g.clearRect(0, 0, width, height);

		// Draw the "red zone" at the bottom of the frame. This zone is 10 px
		// thick, ending at maxY.
		g.setColor(Color.red);
		g.fillRect(0, maxY - 10, width, height);

		// Draw the words.
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		// Loop through the on-screen words and paint them at their coordinates.
		for (int i = 0; i < noWords; i++) {
			// g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
			g.drawString(words[i].getWord(), words[i].getX(),
					words[i].getY() + 20); // y-offset for skeleton so that you
											// can see the words
		}

	}

	/**
	 * Create a WordPanel with an array of WordRecord objects and a maxY value.
	 * 
	 * @param words
	 *            WordRecord array holding the words in the game.
	 * @param maxY
	 *            Maximum Y value for the words.
	 */
	WordPanel(WordRecord[] words, int maxY) {
		this.words = words; // will this work?
		noWords = words.length;
		done = false;
		this.maxY = maxY;
	}

	/**
	 * Run method for the thread, animating the words.
	 */
	public void run() {
		// add in code to animate this
	}

}
