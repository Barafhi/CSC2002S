//package assignment_2;

/**
 * A class to perform vector operations.
 * 
 * @author Michael Evans
 *
 */
public class Vector {

	private double x, y;
	public static final int RECT = 1, POL = 2;

	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}

	public void setRectVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setPolVector(double magnitude, double direction) {
		this.x = magnitude * Math.cos(direction);
		this.y = magnitude * Math.sin(direction);
	}

	public void add(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}

	public void sub(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
	}

	public void mult(double s) {
		this.x *= s;
		this.y *= s;
	}

	public void div(double s) {
		if (s != 0) {
			this.x /= s;
			this.y /= s;
		}
	}

	public double distance(Vector v) {
		return getMagnitude(sub(this, v));
	}

	public double angle(Vector v) {
		return dot(this, v) / (this.getMagnitude() * v.getMagnitude());
	}

	public void normalize() {
		div(this.getMagnitude());
	}

	public void limit(double magnitude) {
		if (this.getMagnitude() > magnitude) {
			this.setMagnitude(magnitude);
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getMagnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public void setMagnitude(double magnitude) {
		if (this.getMagnitude() == 0) {
			return;
		}
		double m = magnitude / this.getMagnitude();
		this.x *= m;
		this.y *= m;
	}

	public String getCoordinates() {
		return "(" + getX() + "," + getY() + ")";
	}

	// ERROR???????????????????????
	// CHECK for 0 magnitude, x=0 and other issues.
	public double getDirection() {
		return Math.atan2(this.y, this.x);
	}

	public void setDirection(double direction) {
		this.x = this.getMagnitude() * Math.cos(direction);
		this.y = this.getMagnitude() * Math.sin(direction);
	}

	public String toString() {
		return "(" + getX() + "," + getY() + ")";
	}

	// Static methods
	public static Vector add(Vector u, Vector v) {
		return new Vector(u.x + v.x, u.y + v.y);
	}

	public static Vector sub(Vector u, Vector v) {
		return new Vector(u.x - v.x, u.y - v.y);
	}

	public static double dot(Vector u, Vector v) {
		return u.x * v.x + u.y * v.y;
	}

	public static Vector mult(Vector v, double s) {
		return new Vector(v.x * s, v.y * s);
	}

	public static Vector div(Vector v, double s) {
		if (s == 0) {
			return null;
		}
		return new Vector(v.x / s, v.y / s);
	}

	public static double distance(Vector u, Vector v) {
		return getMagnitude(sub(u, v));
	}

	public static double angle(Vector u, Vector v) {
		return dot(u, v) / (u.getMagnitude() * v.getMagnitude());
	}

	public static Vector normalize(Vector v) {
		return div(v, v.getMagnitude());
	}

	public static Vector limit(Vector v, double magnitude) {
		if (v.getMagnitude() > magnitude) {
			Vector w = new Vector(v);
			w.setMagnitude(magnitude);
			return w;
		}
		return v;
	}

	public static Vector random() {
		return normalize(new Vector(Math.random() - 0.5, Math.random() - 0.5));
	}

	public static double getMagnitude(Vector v) {
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
}
