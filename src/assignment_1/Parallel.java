//EVNMIC009
//Micahel Evans
//Parallel.java

package assignment_1;

import java.util.concurrent.ForkJoinPool;

public class Parallel {
	private static long startTime = 0;
	static final ForkJoinPool fjPool = new ForkJoinPool();
	int cutoff = 500;

	public Parallel() {
	}

	public Parallel(int cutoff) {
		this.cutoff = cutoff;
	}

	// Initiates the correlation and timing.
	public int correlate(float[] transmitted, float[] received) {
		tick();
		Pair ans = fjPool.invoke(new ParallelSplit(transmitted, received, 0,
				received.length, cutoff));
		float time = toc();
		System.out.println("Parallel run:");
		System.out.println("Max:" + ans.max + ", Pos:" + ans.pos);
		System.out.println("Run took " + time + " seconds\n");

		return ans.pos;
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
