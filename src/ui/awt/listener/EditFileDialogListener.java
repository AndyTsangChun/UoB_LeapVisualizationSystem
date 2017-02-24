package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import system.model.LVSPanel;
import ui.awt.dialog.edit.file.LVSFileEditDialog;

/** ************************************************************
 * This is a listener implementing awt.event.ActionListener
 * Handles all the action in editPanel
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class EditFileDialogListener implements ActionListener{
	private LVSFileEditDialog fileEditPanel;

	/**
	 * Constructor
	 * @param fileEditPanel 
	 */
	public EditFileDialogListener(LVSFileEditDialog fileEditPanel) {
		this.fileEditPanel = fileEditPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// Due to java 1.7 and 1.8 don't support vtk graphics this part has to convert to if-else
		if (command.equals(LVSPanel.COMMAND_CANCEL)){
			// Do sth to cancel
			fileEditPanel.getSwingController().closeEditPanel();
		}else if (command.equals(LVSPanel.COMMAND_DELETE)){
			// Do sth to delete selected
			fileEditPanel.deleteObj();
		}else if (command.equals(LVSPanel.COMMAND_APPLY)){
			// Do sth to apply change
			fileEditPanel.getSwingController().closeEditPanel();
		}
	}

}
