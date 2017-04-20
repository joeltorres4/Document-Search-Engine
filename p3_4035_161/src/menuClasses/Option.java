package menuClasses;

/**
 * Defines the types of options that a menu contains. An object of this type
 * contains a description string (wht the menu displays corresponding to that
 * particular option) and an associated action (another object, of type Action)
 * 
 * @author Joel Torres
 *
 */
public class Option {
	public static final Option EXIT = new Option("Exit", new ExitAction());
	private String description;
	private Action action;

	/**
	 * Constructor for Option objects, carrying a description of the
	 * corresponding option and the action to execute.
	 * 
	 * @param description
	 *            description of option
	 * @param action
	 *            action to execute
	 */
	public Option(String description, Action action) {
		this.description = description;
		this.action = action;
	}

	/**
	 * Getter for description of Option instance.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for Action instance regarding this Option object.
	 * 
	 * @return the action instance.
	 */
	public Action getAction() {
		return action;
	}

}
