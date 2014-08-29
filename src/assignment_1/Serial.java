//EVNMIC009
//Micahel Evans
//Serial.java

package assignment_1;

public class Serial {
	private static long startTime = 0;

	public Serial() {
	}

	// Correlates the two arrays serially
	public int correlate(float[] transmitted, float[] received) {
		tick();
		float max = 0;
		int pos = 0;
		for (int i = 0; i < received.length; ++i) {
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
		float time = toc();
		System.out.println("Serial run:");
		System.out.println("Max:" + max + ", Pos:" + pos);
		System.out.println("Run took " + time + " seconds\n");
		return pos;
	}

	// Start timing.
	private static void tick() {
		startTime = System.currentTimeMillis();
	}

	// Stop timing.
	private static float toc() {
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}

	// Find the maximum serially.
	public int max(float[] array) {
		float max = 0;
		int pos = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				pos = i;
				max = array[i];
			}
		}
		System.out.println("Max:" + max + ", Pos:" + pos);
		return pos;
	}
}
