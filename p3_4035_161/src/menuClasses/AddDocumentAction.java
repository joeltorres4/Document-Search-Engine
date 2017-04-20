package menuClasses;

import java.io.IOException;

import ioManagementClasses.IOComponent;
import systemClasses.SystemController;

/**
 * Action to execute the document addition to system.
 * 
 * @author Joel Torres
 *
 */
public class AddDocumentAction implements Action {

	/**
	 * Executes the addition of documents by receiving the desired filename
	 * input from user and calling the corresponding add method inside System
	 * Controller.
	 */
	public void execute(Object arg) {
		SystemController sc = (SystemController) arg;
		IOComponent io = IOComponent.getComponent();
		io.output("\nAdding a new document to the index system:\n");
		String docName = io.getInput("\nEnter name of new document: ").trim();
		String statusMSG = null;
		try {
			statusMSG = sc.addNewDocument(docName);
			io.output(statusMSG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
