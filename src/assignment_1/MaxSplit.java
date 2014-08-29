//EVNMIC009
//Micahel Evans
//MaxSplit.java

package assignment_1;

import java.util.concurrent.RecursiveTask;

public class MaxSplit extends RecursiveTask<Pair> {
	private float[] array;
	private int lo, hi, cutoff = 500;

	public MaxSplit(float[] array, int lo, int hi) {
		this.array = array;
		this.lo = lo;
		this.hi = hi;
	}

	public MaxSplit(float[] array, int lo, int hi, int cutoff) {
		this.array = array;
		this.lo = lo;
		this.hi = hi;
		this.cutoff = cutoff;
	}

	// Splits the array until the cutoff is reached and then finds the maximum
	// value and its position.
	public Pair compute() {
		// If the cutoff is reached, the maximum in the working array is found
		// sequentially.
		if ((hi - lo) < cutoff) {
			float max = 0;
			int pos = 0;
			for (int i = lo; i < hi; ++i) {
				if (array[i] > max) {
					pos = i;
					max = array[i];
				}
			}
			return new Pair(max, pos);
		} else {
			// Splits the array further and then returns the maximum of the two
			// parts.
			MaxSplit left = new MaxSplit(array, lo, (hi + lo) / 2, cutoff);
			MaxSplit right = new MaxSplit(array, (hi + lo) / 2, hi, cutoff);
			left.fork();
			Pair rightAns = right.compute();
			Pair leftAns = left.join();
			return Pair.max(rightAns, leftAns);
		}
	}
}