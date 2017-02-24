package system.general;

import system.controller.SystemController;

/** ************************************************************
 * This class is use to launch the client application.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ClientLauncher {
	public static void main(String[] args) {

		// Create a sample listener and controller
		SystemController systemController = new SystemController();
		try{
			systemController.run();
		} catch (InternalError e){
			String m = "Unknow java internal error";
			systemController.getMessageFactory().showMessageDialog(2, m, null);
			System.out.println(m);
		}
	}
}
