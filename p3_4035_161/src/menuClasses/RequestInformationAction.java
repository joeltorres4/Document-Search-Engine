package menuClasses;

import ioManagementClasses.IOComponent;
import systemClasses.SystemController;

/**
 * Action for requesting information about document(s).
 * 
 * @author Joel Torres
 *
 */
public class RequestInformationAction implements Action {

	/**
	 * Interface implementation of execute method which receives input of
	 * document to display and calls the corresponding file status method inside
	 * System Controller.
	 */
	public void execute(Object arg) {

		SystemController sc = (SystemController) arg;
		IOComponent io = IOComponent.getComponent();
		io.output("\nDisplaying system documents status:\n");
		String docName = io.getInput("Enter the name of the document to display info: ").trim();
		String statusMSG = null;
		statusMSG = sc.fileStatus(docName);
		io.output(statusMSG);

	}

}
