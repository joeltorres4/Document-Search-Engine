package menuClasses;

/**
 * Any object of this type must have a method that executes necessary algorithm
 * to carry-out the action it represents.
 * 
 * @author Joel Torres
 *
 */
public interface Action {
	/**
	 * Interface method to execute a certain action in the program.
	 * 
	 * @param args
	 *            param to use within execution of this method
	 */
	void execute(Object args);
}
