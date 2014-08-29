//EVNMIC009
//Micahel Evans
//ParallelSplit2.java

package assignment_1;

import java.util.concurrent.RecursiveTask;

public class ParallelSplit2 extends RecursiveTask<float[]> {

	int lo;
	int hi;
	float[] transmitted;
	float[] received;
	int cutoff = 500;

	public ParallelSplit2(float[] transmitted, float[] received, int lo,
			int hi, int cutoff) {
		this.cutoff = cutoff;
		this.lo = lo;
		this.hi = hi;
		this.transmitted = transmitted;
		this.received = received;
	}

	int ans = 0;

	protected float[] compute() {
		// If the cutoff is reached, the working arrays are correlated serially.
		if ((hi - lo) < cutoff) {
			float[] ans = new float[hi - lo];
			for (int i = lo; i < hi; ++i) {
				float sum = 0;
				for (int j = 0; j < transmitted.length; ++j) {
					if (i + j < received.length)
						sum += received[i + j] * transmitted[j];
				}
				ans[i - lo] = sum;
			}
			return ans;
		} else {
			// The shift amount is split up and the result arrays are joined
			// together.
			ParallelSplit2 left = new ParallelSplit2(transmitted, received, lo,
					(hi + lo) / 2, cutoff);
			ParallelSplit2 right = new ParallelSplit2(transmitted, received,
					(hi + lo) / 2, hi, cutoff);
			left.fork();
			float[] rightAns = right.compute();
			float[] leftAns = left.join();
			return join(leftAns, rightAns);
		}
	}

	// Joins two arrays.
	private float[] join(float[] array1, float[] array2) {
		float[] ans = new float[array1.length + array2.length];
		System.arraycopy(array1, 0, ans, 0, array1.length);
		System.arraycopy(array2, 0, ans, array1.length, array2.length);
		return ans;
	}
}
