//EVNMIC009
//Micahel Evans
//Assignment_1.java

package assignment_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jfree.data.xy.XYSeries;

public class Assignment_1 {
	// Arrays to hold data form files.
	private float[] transmitted, received;

	public static void main(String args[]) {
		new Assignment_1();
	}

	public Assignment_1() {
		// Options for specifying files, number of runs, program to run and
		// cutoff.
		Scanner input = new Scanner(System.in);
		System.out
				.println("Enter the file of the transmitted signal (Blank for default):");
		String transmit = input.nextLine();
		if (transmit.trim().isEmpty()) {
			transmit = "../../res/transmit.txt";
		}

		System.out
				.println("Enter the file of the received signal (Blank for default):");
		String receive = input.nextLine();
		if (receive.trim().isEmpty()) {
			receive = "../../res/receive.txt";
		}

		loadSignals(transmit, receive);

		int version = 0;
		while (version > 3 || version < 1) {
			System.out
					.println("Which version of the program would you like to run?"
							+ "\n1 - Serial\n2 - Parallel (No correlation array)\n3 - Parallel (Correlation array)");
			version = input.nextInt();
		}

		int cutoff = 0;
		if (version != 1) {
			while (cutoff < 10) {
				System.out
						.println("What sequential cutoff would you like to use? (Minimum 10)");
				cutoff = input.nextInt();
			}
		}

		int runs = 0;
		while (runs < 1) {
			System.out.println("How many times would you like to run it?");
			runs = input.nextInt();
		}

		// char chartYN = ' ';
		// while (chartYN != 'Y' && chartYN != 'N' && chartYN != 'y'
		// && chartYN != 'n') {
		// System.out.println("Would you like to chart the results?(Y/N)");
		// chartYN = input.next().toLowerCase().charAt(0);
		// }

		System.out.println("\nAvailable processors: "
				+ Runtime.getRuntime().availableProcessors() + "\n");

		int offset = 0;
		switch (version) {
		case 1:
			Serial serial = new Serial();
			for (int i = 0; i < runs; i++) {
				offset = serial.correlate(transmitted, received);
			}
			break;
		case 2:
			Parallel parallel = new Parallel(cutoff);
			for (int i = 0; i < runs; i++) {
				offset = parallel.correlate(transmitted, received);
			}
			break;
		case 3:
			Parallel2 parallel2 = new Parallel2(cutoff);
			for (int i = 0; i < runs; i++) {
				offset = parallel2.correlate(transmitted, received);
			}
			break;
		}

		// Took chart option out as it was not working in the command line.

		// if (chartYN == 'y') {
		// System.out.println("Creating chart...");
		// Chart chart = new Chart();
		// XYSeries[] data = {
		// chart.createSeries("Transmitted", transmitted),
		// chart.createSeries("Received", received),
		// chart.createSeries("Transmitted Offset", transmitted,
		// offset) };
		// chart.chartSignals(chart.createDataset(data));
		// System.out.println("Chart created.\n");
		// }
	}

	// Loads files into arrays.
	public void loadSignals(String transmit, String receive) {
		try {
			System.out.println("Loading " + transmit + "...");
			Scanner input = new Scanner(new File(transmit));
			int dataPoints = input.nextInt();
			transmitted = new float[dataPoints];
			for (int i = 0; i < dataPoints; i++) {
				transmitted[i] = input.nextFloat();
			}
			System.out.println("Loaded " + transmit + "\n");

			System.out.println("Loading " + receive + "...");
			input = new Scanner(new File(receive));
			dataPoints = input.nextInt();
			received = new float[dataPoints];
			for (int i = 0; i < dataPoints; i++) {
				received[i] = input.nextFloat();
			}
			System.out.println("Loaded " + receive + "\n");

			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("A file could not be found. Quitting...");
			System.exit(0);
		}
	}
}