package systemClasses;

import java.io.IOException;

/**
 * Initiates the execution of P3's system
 * 
 * @author Joel Torres
 *
 */
public class Main {

	/**
	 * Main method. Begins execution of system by initializing System Controller
	 * object and executing run and close method for loading and saving info.
	 * 
	 * @param args
	 *            String array with execution inputs (if any)
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the corresponding directory.
	 */
	public static void main(String[] args) throws IOException {
		SystemController sc = SystemController.getInstance();
		sc.run(); // initiates the execution of system controller
		sc.close(); // saves modified index data
	}
}
