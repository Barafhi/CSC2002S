//EVNMIC009
//Micahel Evans
//Pair.java

package assignment_1;

public class Pair {
	float max;
	int pos;

	public Pair(float max, int pos) {
		this.max = max;
		this.pos = pos;
	}

	// Compares two Pair objects and returns the one with a higher max value.
	public static Pair max(Pair one, Pair two) {
		if (one.max > two.max)
			return one;
		return two;
	}

}
