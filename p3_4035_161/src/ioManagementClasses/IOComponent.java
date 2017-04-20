package ioManagementClasses;

import java.util.Scanner;

/**
 * Singlenton classs to manage I/O
 * 
 * @author Joel Torres
 *
 */
public class IOComponent {
	private static final IOComponent COMPONENT = new IOComponent();
	private Scanner sc;

	/**
	 * Default constructor. Initializes scanner object.
	 */
	private IOComponent() {
		sc = new Scanner(System.in);
	}

	/**
	 * Getter for IOComponent object.
	 * 
	 * @return the component
	 */
	public static IOComponent getComponent() {
		return COMPONENT;
	}

	/**
	 * Getter for input. Prints msg and receives next line input.
	 * 
	 * @param msg
	 *            message to print
	 * @return input from user
	 */
	public String getInput(String msg) {
		System.out.print(msg);
		return sc.nextLine();
	}

	/**
	 * Prints line to console.
	 * 
	 * @param line
	 *            line to print
	 */
	public void output(String line) {
		System.out.print(line);
	}

	/**
	 * Getter for integer input. Prints given message and receives a parsed
	 * integer input.
	 * 
	 * @param msg
	 *            message to print
	 * @return integer input
	 */
	public int getInputInteger(String msg) {
		int value = 0; // value to read
		boolean repite;
		do {
			repite = false;
			try {
				value = Integer.parseInt(this.getInput(msg));
			} catch (Exception e) {
				repite = true;
				System.out.println("  ... invalid integer --");
			}
		} while (repite);

		return value;
	}
}
