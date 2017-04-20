package menuClasses;

import systemClasses.SystemController;

/**
 * Action to execute the system exit.
 * 
 * @author Joel Torres
 *
 */
public class ExitAction implements Action {

	/**
	 * Executes the system exit by emptying menu stack, resulting in system
	 * termination.
	 */
	public void execute(Object arg) {
		SystemController sc = (SystemController) arg;
		sc.getMenuStack().pop();
	}

}
