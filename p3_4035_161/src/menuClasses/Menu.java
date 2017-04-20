package menuClasses;

import java.util.ArrayList;

import ioManagementClasses.IOComponent;

/**
 * Abstract class to represent Menu objects. Has all base functions for a menu
 * to work.
 * 
 * @author Joel Torres
 *
 */
public abstract class Menu {
	private String menuString;
	private ArrayList<Option> options;

	/**
	 * Initializes the menu.
	 * 
	 * @param title
	 *            the title of the menu
	 * @param options
	 *            the list of options to be displayed by the menu. Each option
	 *            is displayed numbered 1, 2, .... That number is the number
	 *            used to determine the user's preference when a menu is
	 *            displayed.
	 */
	protected void InitializeMenu(String title, ArrayList<Option> options) {
		this.options = options;
		menuString = "\n\n" + title + "\n";
		for (int i = 0; i < options.size(); i++) {
			Option opt = options.get(i);
			menuString += " <" + (i + 1) + "> " + opt.getDescription() + "\n";
		}
		menuString += "\nEnter your selection: ";
	}

	/**
	 * Activates the menu: displays the different options, request a selection
	 * from user, validate, and return the option corresponding to the user's
	 * selection.
	 * 
	 * @return instance of Option object
	 */
	public Option activate() {
		displayOptions();
		int optIndex = getSelectionFromUser();
		return options.get(optIndex - 1);
	}

	/**
	 * Adds a new option to this Menu object.
	 * 
	 * @param opt
	 *            option to add
	 */
	protected void addOption(Option opt) {
		options.add(opt);
	}

	/**
	 * Displays current options in this Menu object.
	 */
	private void displayOptions() {
		IOComponent.getComponent().output(menuString);
	}

	/**
	 * Reads and validates user selection
	 * 
	 * @return selection from user
	 */
	private int getSelectionFromUser() {
		boolean validSelection = false;
		int selection = 0; // initialize to comply with compiler
		while (!validSelection) {
			String input = IOComponent.getComponent().getInput("");
			try {
				selection = Integer.parseInt(input);
				if (selection >= 1 && selection <= options.size())
					validSelection = true;
			} catch (Exception e) {
				// neglect that a non integer value has been entered
				// just write a message
			}
			if (!validSelection) {
				IOComponent.getComponent()
						.output("***ERROR: Enter an int value in range 1 to " + options.size() + ".\n");
				displayOptions();
			}
		}
		return selection;
	}

}
