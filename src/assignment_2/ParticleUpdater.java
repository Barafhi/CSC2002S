//package assignment_2;

import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleUpdater implements Runnable {

	private volatile boolean running;
	private volatile long[] lastLoopTime;

	private CopyOnWriteArrayList<Particle> particles;
	private long last;

	public ParticleUpdater(CopyOnWriteArrayList<Particle> particles) {
		running = true;
		this.particles = particles;
	}

	@Override
	public void run() {
		running = true;
		last = System.currentTimeMillis();
		while (running) {
			// Update any particle positions.
			long delta = System.currentTimeMillis() - last;
			Particle.update(particles, delta);
			last = System.currentTimeMillis();
			Particle.kill(particles);
		}
	}

	/**
	 * Sets a flag to stop the running thread.
	 */
	public void requestStop() {
		running = false;
	}

	public void explode(int x, int y) {
		for (int i = 0; i < 300; i++) {
			Particle p = new Particle(new Vector(x, y));
			particles.add(p);
		}
	}
}
