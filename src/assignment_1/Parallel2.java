//EVNMIC009
//Micahel Evans
//Parallel2.java

package assignment_1;

import java.util.concurrent.ForkJoinPool;

public class Parallel2 {
	private static long startTime = 0;
	static final ForkJoinPool fjPool = new ForkJoinPool();
	int cutoff = 500;

	public Parallel2() {
	}

	public Parallel2(int cutoff) {
		this.cutoff = cutoff;
	}

	// Initiates the correlation and timing.
	public int correlate(float[] transmitted, float[] received) {
		tick();
		float[] ans = fjPool.invoke(new ParallelSplit2(transmitted, received,
				0, received.length, cutoff));
		float time = toc();
		Pair maximumPair = max(ans);
		float max = maximumPair.max;
		int pos = maximumPair.pos;
		System.out.println("Parallel run:");
		System.out.println("Max:" + max + ", Pos:" + pos);
		System.out.println("Run took " + time + " seconds\n");
		return pos;
	}

	// Initiates the max finding.
	public static Pair max(float[] array) {
		return fjPool.invoke(new MaxSplit(array, 0, array.length));
	}

	// Start timing.
	private static void tick() {
		startTime = System.currentTimeMillis();
	}

	// Stop timing.
	private static float toc() {
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}
}
