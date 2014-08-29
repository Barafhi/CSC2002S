//EVNMIC009
//Micahel Evans
//ParallelSplit.java

package assignment_1;

import java.util.concurrent.RecursiveTask;

public class ParallelSplit extends RecursiveTask<Pair> {

	int lo;
	int hi;
	float[] transmitted;
	float[] received;
	int cutoff = 500;

	public ParallelSplit(float[] transmitted, float[] received, int lo, int hi,
			int cutoff) {
		this.cutoff = cutoff;
		this.lo = lo;
		this.hi = hi;
		this.transmitted = transmitted;
		this.received = received;
	}

	int ans = 0;

	protected Pair compute() {
		// If the cutoff is reached, the working arrays are correlated serially.
		// Only the maximum value is kept.
		if ((hi - lo) < cutoff) {
			float max = 0;
			int pos = 0;
			for (int i = lo; i < hi; ++i) {
				float sum = 0;
				for (int j = 0; j < transmitted.length; ++j) {
					if (i + j < received.length) {
						sum += received[i + j] * transmitted[j];
					}
				}
				if (sum > max) {
					max = sum;
					pos = i;
				}
			}
			return new Pair(max, pos);
		} else {
			// The shift amount is split up and the max of the results is
			// returned.
			ParallelSplit left = new ParallelSplit(transmitted, received, lo,
					(hi + lo) / 2, cutoff);
			ParallelSplit right = new ParallelSplit(transmitted, received,
					(hi + lo) / 2, hi, cutoff);
			left.fork();
			Pair rightAns = right.compute();
			Pair leftAns = left.join();
			return Pair.max(rightAns, leftAns);
		}
	}
}
