package system.general;

import javax.swing.JOptionPane;

import system.controller.SystemController;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This class is used to produce LVS Message
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSMessageFactory {
	private SystemController systemController;
	
	/**
	 * Constructor
	 * @param	systemController	controller of system
	 */
	public LVSMessageFactory(SystemController systemController){
		this.systemController = systemController;
	}
	
	/**
	 * Show LVS message
	 * 0 = normal message
	 * 1 = warning message
	 * 2 = error message
	 * 3 = option message
	 * 
	 * Value of Result = Position of option array
	 * 
	 * @param type Message Type
	 * @param string content of message
	 */
	public int showMessageDialog(int type, String string, String[] options){
		int result = -1; 
		switch(type){
		case 0:
			JOptionPane.showMessageDialog(systemController.getSwingController().getFrame(), string, LVSStringInfo.PLAIN_MESSAGE_TITLE, JOptionPane.PLAIN_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(systemController.getSwingController().getFrame(), string, LVSStringInfo.WARNING_MESSAGE_TITLE, JOptionPane.WARNING_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(systemController.getSwingController().getFrame(), string, LVSStringInfo.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
			break;
		case 3:
			result = JOptionPane.showOptionDialog(systemController.getSwingController().getFrame(), string,
				    LVSStringInfo.PLAIN_MESSAGE_TITLE,
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.PLAIN_MESSAGE,
				    null,     //do not use a custom Icon
				    options,  //the titles of buttons
				    options[0]); //default button title
			break;
		case 4:
			JOptionPane.showMessageDialog(systemController.getSwingController().getFrame(), string, LVSStringInfo.PLAIN_MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
			break;
		default:
			System.out.println("Error");
		}
		return result;
	}
}
