//package assignment_2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class to create and manipulate particles.
 * 
 * @author Micahel Evans
 *
 */
public class Particle {

	protected Vector displacement, velocity, acceleration;
	protected Color colour;
	protected double life = 10;
	protected boolean isDead = false;

	/**
	 * Create a particle with a given displacement, random velocity and random
	 * colour.
	 * 
	 * @param displacement
	 *            The initial displacement vector for the particle.
	 */
	public Particle(Vector displacement) {
		this.displacement = displacement;
		this.colour = randomColour();
		this.velocity = Vector.random();
		this.velocity.mult(random(50, 300));
		this.velocity.add(new Vector(0, -500));
		this.acceleration = new Vector(0, 1000);
		this.life = random(0.1, 2.0);
	}

	/**
	 * Kill and remove dead particles from the list.
	 * 
	 * @param particles
	 *            List of particles.
	 */
	public static synchronized void kill(
			CopyOnWriteArrayList<Particle> particles) {
		ArrayList<Particle> deadParticles = new ArrayList<Particle>();
		for (Particle p : particles) {
			if (p.isDead) {
				deadParticles.add(p);
			}
		}
		particles.removeAll(deadParticles);
		deadParticles.clear();
	}

	/**
	 * Update the particle, adding to its velocity and displacement vectors and
	 * lowering its life.
	 * 
	 * @param delta
	 */
	public synchronized void update(long delta) {
		this.velocity.add(Vector.mult(this.acceleration, calcTime(delta)));
		this.displacement.add(Vector.mult(this.velocity, calcTime(delta)));

		deteriorate(delta);
	}

	/**
	 * Update the particles in the list.
	 * 
	 * @param particles
	 *            List of particles.
	 * @param delta
	 *            Time since last update.
	 */
	public static synchronized void update(
			CopyOnWriteArrayList<Particle> particles, long delta) {
		for (Particle p : particles) {
			p.update(delta);
		}
	}

	/**
	 * Render the particle.
	 * 
	 * @param g
	 *            The graphics object on which to render.
	 */
	public synchronized void render(Graphics g) {
		g.setColor(this.colour);
		g.fillRect((int) this.displacement.getX(),
				(int) this.displacement.getY(), 4, 4);
	}

	/**
	 * Render the particles in the list.
	 * 
	 * @param particles
	 *            List of particles.
	 * @param g
	 *            The graphics object on which to render.
	 */
	public static synchronized void render(
			CopyOnWriteArrayList<Particle> particles, Graphics g) {
		for (Particle p : particles) {
			p.render(g);
		}
	}

	/**
	 * Lower the life of the particle.
	 * 
	 * @param delta
	 *            Time since last update.
	 */
	public synchronized void deteriorate(long delta) {
		life -= calcTime(delta);
		if (life <= 0) {
			kill();
		}
	}

	/**
	 * Kill the particle.
	 */
	public synchronized void kill() {
		isDead = true;
	}

	public synchronized Vector getDisplacement() {
		return displacement;
	}

	public synchronized void setDisplacement(Vector displacement) {
		this.displacement = displacement;
	}

	public synchronized Vector getVelocity() {
		return velocity;
	}

	public synchronized void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public synchronized Vector getAcceleration() {
		return acceleration;
	}

	public synchronized void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}

	public synchronized Color getColour() {
		return colour;
	}

	public synchronized void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Convert a time in milliseconds to a time in seconds.
	 * 
	 * @param millis
	 *            Time in milliseconds.
	 * @return Time in seconds.
	 */
	private static double calcTime(long millis) {
		return (double) millis / 1000;
	}

	/**
	 * Get a random double between two numbers.
	 * 
	 * @param a
	 *            Lower limit.
	 * @param b
	 *            Upper limit.
	 * @return Random double.
	 */
	private static double random(double a, double b) {
		return (Math.random() * (b - a)) + a;
	}

	/**
	 * Get a random colour.
	 * 
	 * @return A random colour.
	 */
	private static Color randomColour() {
		return new Color((int) (Math.random() * -0b1000000000000000000000000));
	}
}
