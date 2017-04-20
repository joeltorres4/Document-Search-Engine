package menuClasses;

import ioManagementClasses.IOComponent;
import systemClasses.SystemController;

/**
 * Action to remove a document from the system. Receives the name of the
 * document to remove and executes the corresponding functions inside System
 * Controller.
 * 
 * @author Joel Torres
 *
 */
public class RemoveDocumentAction implements Action {

	/**
	 * Interface implementation of execute method which receives input filename
	 * and calls the remove method.
	 */
	public void execute(Object arg) {
		SystemController sc = (SystemController) arg;
		IOComponent io = IOComponent.getComponent();
		io.output("\nRemoving a document from the system:\n");
		String docName = io.getInput("\nEnter name of the document to remove: ").trim();
		String statusMSG = null;
		statusMSG = sc.removeDocument(docName);
		io.output(statusMSG);
	}

}
