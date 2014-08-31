//package assignment_2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Particle {

	protected Vector displacement, velocity, acceleration;
	protected Color colour;
	protected double life = 10;
	protected boolean isDead = false;

	protected Canvas canvas;

	public Particle(Vector displacement) {
		this.displacement = displacement;
		this.colour = randomColour();
		this.velocity = Vector.random();
		this.velocity.mult(random(50, 400));
		this.acceleration = new Vector(0, 1000);
		this.life = random(0.1, 2.0);
	}

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

	public void update(long delta) {
		this.velocity.add(Vector.mult(this.acceleration, calcTime(delta)));
		this.displacement.add(Vector.mult(this.velocity, calcTime(delta)));

		deteriorate(delta);
	}

	public static synchronized void update(
			CopyOnWriteArrayList<Particle> particles, long delta) {
		for (Particle p : particles) {
			p.update(delta);
		}
	}

	public void render(Graphics g) {
		g.setColor(this.colour);
		g.fillRect((int) this.displacement.getX(),
				(int) this.displacement.getY(), 5, 5);
	}

	public static synchronized void render(
			CopyOnWriteArrayList<Particle> particles, Graphics g) {
		for (Particle p : particles) {
			p.render(g);
		}
	}

	public synchronized void deteriorate(long delta) {
		life -= calcTime(delta);
		if (life <= 0) {
			kill();
		}
	}

	public void kill() {
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

	public static double calcTime(long millis) {
		return (double) millis / 1000;
	}

	public static double random(double a, double b) {
		return (Math.random() * (b - a)) + a;
	}

	public static Color randomColour() {
		return new Color((int) (Math.random() * -0b1000000000000000000000000));
	}
}
