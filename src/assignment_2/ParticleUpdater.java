//package assignment_2;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class to update the Particles in the game.
 * 
 * @author Michael Evans
 *
 */
public class ParticleUpdater implements Runnable {
	private volatile boolean running = false;
	// List of particles.
	private CopyOnWriteArrayList<Particle> particles;
	// Time of last update.
	private long lastLoopTime;

	/**
	 * Create a new ParticleUpdater to update a list of particles.
	 * 
	 * @param particles
	 *            The list of particles.
	 */
	ParticleUpdater(CopyOnWriteArrayList<Particle> particles) {
		this.particles = particles;
	}

	@Override
	public void run() {
		running = true;
		lastLoopTime = System.currentTimeMillis();
		while (running) {
			// Update any particle positions.
			long delta = System.currentTimeMillis() - lastLoopTime;
			Particle.update(particles, delta);
			lastLoopTime = System.currentTimeMillis();
			// Remove any dead particles.
			Particle.kill(particles);
		}
	}

	/**
	 * Sets a flag to stop the running thread.
	 */
	public void requestStop() {
		running = false;
	}

	/**
	 * Create an explosion of 300 randomly coloured particles.
	 * 
	 * @param x
	 *            X coordinate of the explosion.
	 * @param y
	 *            Y coordinate of the explosion.
	 */
	public void explode(int x, int y) {
		for (int i = 0; i < 300; i++) {
			Particle p = new Particle(new Vector(x, y));
			particles.add(p);
		}
	}

	/**
	 * Create an explosion of 50 white particles.
	 * 
	 * @param x
	 *            X coordinate of the explosion.
	 * @param y
	 *            Y coordinate of the explosion.
	 */
	public void implode(int x, int y) {
		for (int i = 0; i < 50; i++) {
			Particle p = new Particle(new Vector(x, y));
			p.setColour(Color.white);
			particles.add(p);
		}
	}
}
