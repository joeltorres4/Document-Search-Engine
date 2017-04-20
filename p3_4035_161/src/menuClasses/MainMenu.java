package menuClasses;

import java.util.ArrayList;

/**
 * Main Menu object. This is where the main menu is created and the
 * corresponding options actions are set.
 * 
 * @author Joel Torres
 *
 */
public class MainMenu extends Menu {
	private static final MainMenu MM = new MainMenu();

	/**
	 * Main Menu object default constructor. Initializes main menu with the
	 * options desired (in this case, 5 options).
	 */
	private MainMenu() {
		super();
		String title;
		ArrayList<Option> options = new ArrayList<Option>();
		title = "Main Menu";
		options.add(new Option("Add a new document", new AddDocumentAction()));
		options.add(new Option("Remove a document", new RemoveDocumentAction()));
		options.add(new Option("Request information about a document", new RequestInformationAction()));
		options.add(new Option("Perform searches based on words", new PerformSearchesAction()));
		options.add(Option.EXIT);

		super.InitializeMenu(title, options);
	}

	/**
	 * Getter for main menu instance.
	 * 
	 * @return main menu object.
	 */
	public static MainMenu getMainMenu() {
		return MM;
	}
}
