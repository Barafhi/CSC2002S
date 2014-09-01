//package assignment_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

/**
 * A class to display the falling words.
 * 
 * @author Michael Evans (adapted from skeleton code provided by Michelle
 *         Kuttel)
 *
 */
@SuppressWarnings("serial")
public class WordPanel extends JPanel implements Runnable {
	private static volatile boolean running = false;
	// WordUpdater for updating the words.
	private static WordUpdater wordUpdater;
	// WordRecords.
	private WordRecord[] words;
	private int noWords;
	private int maxY;
	// ParticleUpdater for updating the particles.
	private static ParticleUpdater particleUpdater;
	// Particles.
	private CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<Particle>();

	/**
	 * Repaint the game screen with updated word positions.
	 */
	public void paintComponent(Graphics g) {
		// Dimensions of the frame.
		int width = this.getWidth();
		int height = this.getHeight();
		// Clear the frame in order to repaint the words.
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		// Draw the "red zone" at the bottom of the frame. This zone is 10
		// pixels thick.
		g.setColor(Color.getHSBColor(353, (float) 0.85, (float) 1));
		g.fillRect(0, maxY - 10, width, height);
		// If the game is running, paint the rest.
		if (running) {
			// Draw any particles behind the words.
			Particle.render(particles, g);
			// Draw the words.
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", Font.PLAIN, 26));
			// Loop through the on-screen words and paint them at their
			// coordinates.
			for (int i = 0; i < noWords; i++) {
				if (words[i].falling())
					g.drawString(words[i].getWord(), words[i].getX() + 5,
							words[i].getY() + 6);
			}
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
		this.words = words;
		noWords = words.length;
		this.maxY = maxY;
		// Update controllers.
		wordUpdater = new WordUpdater(words);
		particleUpdater = new ParticleUpdater(particles);
	}

	/**
	 * Run method for the thread, animating the words.
	 */
	public void run() {
		running = true;
		// Start threads to update graphics.
		Thread wordUpdaterThread = new Thread(wordUpdater);
		wordUpdaterThread.start();
		Thread particleUpdaterThread = new Thread(particleUpdater);
		particleUpdaterThread.start();
		// Repaint and sleep.
		while (running) {
			repaint();
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets a flag to stop the running thread.
	 */
	public void requestStop() {
		wordUpdater.requestStop();
		particleUpdater.requestStop();
		particles.clear();
		repaint();
		running = false;
	}

	/**
	 * Create an explosion.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 */
	public void explode(int x, int y) {
		particleUpdater.explode(x, y);
	}

	/**
	 * Create an implosion.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 */
	public void implode(int x, int y) {
		particleUpdater.implode(x, y);
	}
}
